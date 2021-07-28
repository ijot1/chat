package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.exception.ResourceNotFoundException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
                .userUsersRooms(new HashSet<>())
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
                .userUsersRooms(new HashSet<>())
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
                .userUsersRooms(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        em.persist(user3);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
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
        User u1 = em.merge(user1);
        UserRoom uR2 = em.merge(userRoom2);
        u1.getUserUsersRooms().add(uR2);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User u2 = em.merge(user2);
        UserRoom uR1 = em.merge(userRoom1);
        u2.getUserUsersRooms().add(uR1);
        em.getTransaction().commit();
        em.close();

    }

    @After
    public void testCleanUpAfterExecutionUserRoom() {
            userRepository.deleteAll();
            roomRepository.deleteAll();
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
        UserRoom userRoom1 = (UserRoom) userRoomRepository.findAll().toArray()[0];
        UserRoomId uR1Id = userRoom1.getId();

        //When
        UserRoom userRoom = userRoomRepository.findById(uR1Id).orElseThrow(
                () -> new ResourceNotFoundException("UserRoom not found")
        );
        String userRoomUserName = userRoom.getUser().getName();

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
        //@Before prepared data

        //When
        UserRoom uR1 = (UserRoom) userRoomRepository.findAll().toArray()[0];
        userRoomRepository.delete(uR1);

        int count = userRoomRepository.findAll().size();

        //Then
        Assert.assertEquals(1, count);
    }
}
