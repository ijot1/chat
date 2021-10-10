package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import com.messenger.chat.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRoomRepositoryTestSuite {

    @Autowired
    UserRoomRepository userRoomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RecipientRepository recipientRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emFactory;

    private void cleanUpTestData() {
        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        Room room = (Room) roomRepository.findAll().toArray()[0];

        roomRepository.delete(room);

        userRepository.delete(user1);
        userRepository.delete(user2);
        userRepository.delete(user3);
    }

    @Before
    public void prepareUserRoomRepositoryTestData() {
        emFactory = Persistence.createEntityManagerFactory("ChatPU");

        em = emFactory.createEntityManager();
        em.getTransaction().begin();
        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();
        em.persist(user1);

        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();
        em.persist(user2);

        User user3 = User.builder()
                .id(null)
                .nick("kk")
                .name("Karina-Kranik")
                .sex('W')
                .location("Rytro")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .recipients(new HashSet<>())
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();
        em.persist(user3);

        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();
        em.persist(room);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.merge(user1);
        em.merge(room);
        UserRoom userRoom1 = UserRoom.builder()
                .id(new UserRoomId(user1.getId(), room.getId()))
                .user(user1)
                .room(room)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();
        em.persist(userRoom1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.merge(user2);
        em.merge(room);
        UserRoom userRoom2 = UserRoom.builder()
                .id(new UserRoomId(user2.getId(), room.getId()))
                .user(user2)
                .room(room)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();
        em.persist(userRoom2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        em.merge(user1);
        em.merge(user2);
        em.merge(userRoom1);
        em.merge(userRoom2);
        user1.getUserUsersRooms().add(userRoom2);
        user2.getUserUsersRooms().add(userRoom1);
        em.getTransaction().commit();

    }

    @After
    public void cleanUpRepository() {
        cleanUpTestData();
        if (em != null && em.isOpen())
            em.close();
        if (emFactory != null)
            emFactory.close();
    }

    @Test
    public void testFindAllUsersRooms() {
        //Given
        //@Before prepared data

        //When
        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(2, count);
    }

    @Test
    public void testFindUserRoomById() {
        //Given
        //@Before prepared data
        User user1 = (User) userRepository.findAll().toArray()[0];
        Room room = (Room) roomRepository.findAll().toArray()[0];

        em.getTransaction().begin();
        em.merge(user1);
        em.merge(room);
        UserRoomId userRoomId = new UserRoomId(user1.getId(), room.getId());
        em.getTransaction().commit();

        //When
        UserRoom userRoom = userRoomRepository.findById(userRoomId).orElseThrow(
                () -> new ResourceNotFoundException("UserRoom not found")
        );

        em.getTransaction().begin();
        UserRoom readUserRoom = em.merge(userRoom);
        String userRoomUserName = readUserRoom.getUser().getName();
        em.getTransaction().commit();

        //Then
        Assert.assertEquals("Irena-Janik", userRoomUserName);
    }

    @Test
    public void testFindUsersRoomsByRoomId() {
        //Given
        //@Before prepared data
        UserRoom userRoom = (UserRoom) userRoomRepository.findAll().toArray()[0];
        Long roomId = userRoom.getRoom().getId();

        //When
        int count = userRoomRepository.findByRoomId(roomId).size();

        //Then
        Assert.assertEquals(2, count);
    }

    @Test
    public void testSaveUserRoom() {
        //Given
        //@Before prepared data
        em = emFactory.createEntityManager();

        User user3 = (User) userRepository.findAll().toArray()[2];
        Room room = (Room) roomRepository.findAll().toArray()[0];

        em.getTransaction().begin();
        User readUser3 = em.merge(user3);
        Room readRoom = em.merge(room);
        em.getTransaction().commit();
        em.close();

        UserRoom userRoom = UserRoom.builder()
                .id(new UserRoomId(readUser3.getId(), readRoom.getId()))
                .user(readUser3)
                .room(readRoom)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();
        userRoomRepository.save(userRoom);

        //When
        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(3, count);
    }

    @Test
    public void testDeleteUserRoom() {
        //Given
        em = emFactory.createEntityManager();
        //@Before prepared data
        User user1 = (User) userRepository.findAll().toArray()[0];
        Room room = (Room) roomRepository.findAll().toArray()[0];
        List<Recipient> recipients = recipientRepository.findAll();

        //When
        em.getTransaction().begin();
        User readUser1 = em.getReference(User.class, user1.getId());
        Room readRoom = em.merge(room);

        UserRoom userRoom = em.find(UserRoom.class, new UserRoomId(readUser1.getId(), readRoom.getId()));
        UserRoomId userRoomId = userRoom.getId();
        List<Long> userRoomRecipientsIds = null;
        if (recipients.size() != 0) {
            Query q = em.createNativeQuery("select re.id from recipients re join users_rooms ur " +
                    "on re.id_user = ur.user_id and re.id_room = ur.room_id " +
                    "where ur.id = :id");
            q.setParameter("id", userRoom.getId());
            userRoomRecipientsIds = q.getResultList();
        } else {
            Recipient recipient = new Recipient();
            recipient.setUserRoom(null);
        }

        List<Long> userRecipientsIds = null;
        if (recipients.size() != 0) {
            Query q = em.createNativeQuery("select re.id from recipients re join users u " +
                    "on re.user_id = u.user_id " +
                    "where u.id = :id");
            q.setParameter("id", user1.getId());
            userRecipientsIds = q.getResultList();
        } else {
            Recipient recipient = new Recipient();
            recipient.setUser(null);
        }

        em.createNativeQuery("delete from recipients re where re.id in (:ids)")
        .setParameter("ids", userRoomRecipientsIds)
        .executeUpdate();

        em.createNativeQuery("delete from recipients re where re.id in (:ids)")
                .setParameter("ids", userRecipientsIds)
                .executeUpdate();

        readUser1.getUserUsersRooms().remove(userRoom);
        readRoom.getRoomUsersRooms().remove(userRoom);

        em.flush();
        em.getTransaction().commit();

        em.getTransaction().begin();
        UserRoom uR = em.merge(userRoom);
        em.flush();
        em.getTransaction().commit();

        userRoomRepository.delete(uR);
        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(1, count);
    }

}

