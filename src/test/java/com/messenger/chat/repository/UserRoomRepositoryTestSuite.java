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
import org.springframework.test.context.event.annotation.AfterTestClass;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;

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
    private EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");

    public static Long user1Id;
    public static Long user2Id;
    public static Long user3Id;

    public static Long roomId;

    public static UserRoomId userRoom1Id;
    public static UserRoomId userRoom2Id;

    @Before
    public void prepareUserRoomRepositoryTestData() {
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
        user1Id = user1.getId();
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
        user2Id = user2.getId();
        em.merge(user3);
        user3Id = user3.getId();
        em.merge(room);
        roomId = room.getId();
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
        userRoom1Id = userRoom1.getId();
        em.merge(userRoom2);
        userRoom2Id = userRoom2.getId();
        user1.getUserUsersRooms().add(userRoom2);
        user2.getUserUsersRooms().add(userRoom1);
        em.getTransaction().commit();

    }

    @After
    public void cleanUpRepository() {
        cleanUpTestData();
        if (em != null && em.isOpen())
            em.close();
    }

    @AfterTestClass
    public void closeFactory() {
        if (emFactory != null)
            emFactory.close();
    }

    private void cleanUpTestData() {
        if (userRoomRepository.findAll().size() != 0) {
            userRoomRepository.deleteAll();
        }

        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        User user1 = em.find(User.class, user1Id);
        User user2 = em.find(User.class, user2Id);
        User user3 = em.find(User.class, user3Id);

        Room room = em.find(Room.class, roomId);

        em.remove(user1);
        em.remove(user2);
        em.remove(user3);

        em.remove(room);

        em.getTransaction().commit();
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

        em.getTransaction().begin();
        User user1 = em.find(User.class, user1Id);
        Room room = em.find(Room.class, roomId);
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
        em.getTransaction().begin();

        User user3 = em.find(User.class, user3Id);
        Room room = em.find(Room.class, roomId);

        em.getTransaction().commit();
        em.close();

        UserRoom userRoom = UserRoom.builder()
                .id(new UserRoomId(user3.getId(), room.getId()))
                .user(user3)
                .room(room)
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

        //When
        em.getTransaction().begin();
        User user1 = em.find(User.class, user1Id);
        Room room = em.find(Room.class, roomId);
        List<Recipient> recipients = recipientRepository.findAll();

        UserRoom userRoom = em.find(UserRoom.class, new UserRoomId(user1.getId(), room.getId()));
        UserRoomId userRoomId = userRoom.getId();

        System.out.println("#1 " + userRoomRepository.findAll().size());

        user1.getUserUsersRooms().remove(userRoom);
        room.getRoomUsersRooms().remove(userRoom);

        System.out.println("#2 " + userRoomRepository.findAll().size());

        em.flush();
        em.getTransaction().commit();


        System.out.println("#3 " + userRoomRepository.findAll().size());
        userRoomRepository.delete(userRoom);
        int count = userRoomRepository.findAll().size();
        System.out.println("#4 " + count);

        //Then
        Assert.assertEquals(1, count);
    }

}

