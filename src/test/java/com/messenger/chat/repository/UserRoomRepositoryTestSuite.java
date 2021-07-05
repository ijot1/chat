package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.time.LocalDate;
import java.util.HashSet;

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
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");

    @Before
    public void testPrepareUserRoomRepositoryTestSuite() {
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
                .friends(new HashSet<>())
                .build();

        em.persist(user1);
        em.getTransaction().commit();

        em.getTransaction().begin();
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
                .friends(new HashSet<>())
                .build();

        em.persist(user2);
        em.getTransaction().commit();

        em.getTransaction().begin();
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
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .userRooms(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User readUser1 = em.merge(user1);
        Room readRoom = em.merge(room);
        UserRoom userRoom1 = UserRoom.builder()
                .id(new UserRoomId(readUser1.getId(), readRoom.getId()))
                .user(readUser1)
                .room(readRoom)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();

        em.persist(userRoom1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User readUser2 = em.merge(user2);
        readRoom = em.merge(room);
        UserRoom userRoom2 = UserRoom.builder()
                .id(new UserRoomId(readUser2.getId(), readRoom.getId()))
                .user(readUser2)
                .room(readRoom)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();

        em.persist(userRoom2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User readUser3 = em.merge(user3);
        readRoom = em.merge(room);
        UserRoom userRoom3 = UserRoom.builder()
                .id(new UserRoomId(readUser3.getId(), readRoom.getId()))
                .user(readUser3)
                .room(readRoom)
                .addedOn(LocalDate.now())
                .recipients(new HashSet<>())
                .build();

        em.persist(userRoom3);
        em.getTransaction().commit();

    }

    @After
    public void testCleanUpAfterExecutionUserRoom() {
        Long rId = ((Room) roomRepository.findAll().toArray()[0]).getId();

        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        try {
            roomRepository.deleteById(rId);
            userRepository.delete(user1);
            userRepository.delete(user2);
            userRepository.delete(user3);
        } catch (EntityNotFoundException nfe) {
            nfe.printStackTrace(System.err);
        }
    }

    @Test
    public void testFindAllUsersRooms() {
        //Given
        //@Before prepared data

        //When
        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(3, count);
    }

    @Test
    public void testFindUserRoomById() {
        //Given
        //@Before prepared data
        UserRoom userRoom1 = (UserRoom)userRoomRepository.findAll().toArray()[0];
        UserRoomId uR1Id = userRoom1.getId();

        //When
        UserRoom userRoom = userRoomRepository.findById(uR1Id).orElse(null);
        String userRoomUserName = userRoom.getUser().getName();

        //Then
        Assert.assertEquals("Irena-Janik", userRoomUserName);
    }

    @Test
    public void testSaveUserRoom() {
        //Given
        //@Before prepared data
        em = emFactory.createEntityManager();
        UserRoom uR1 = (UserRoom) userRoomRepository.findAll().toArray()[0];

        em.getTransaction().begin();
        UserRoom readUserRoom1 = em.merge(uR1);
        em.getTransaction().commit();
        em.close();

        userRoomRepository.delete(readUserRoom1);

        int countBeforeSave = userRoomRepository.findAll().size();

        //When
        userRoomRepository.save(readUserRoom1);
        int countAfterSave = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(2, countBeforeSave);
        Assert.assertEquals(3, countAfterSave);
    }

    @Test
    public void testDeleteUserRoom() {
        //Given
        //@Before prepared data

        //When
        UserRoom uR1 = (UserRoom) userRoomRepository.findAll().toArray()[0];
        userRoomRepository.delete(uR1);

        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(2, count);
    }
}
