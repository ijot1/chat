package com.messenger.chat.repository;

import com.messenger.chat.domain.Room;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RoomRepositoryTestSuite {

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    private EntityManagerFactory emFactory;

    @Before
    public void init() {
        emFactory = Persistence.createEntityManagerFactory("ChatPU");
    }

    @After
    public void destroy() {
        if (emFactory != null) {
            emFactory.close();
        }
    }

    @Test
    public void testFindRooms() {
        //Given
        em = emFactory.createEntityManager();

        em.getTransaction().begin();
        Room room1 = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        Room room2 = Room.builder()
                .id(null)
                .name("Java Talks")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room2);
        em.getTransaction().commit();

        //When
        int count = roomRepository.findAll().size();

        Room readRoom1 = (Room) roomRepository.findAll().toArray()[0];
        Room readRoom2 = (Room) roomRepository.findAll().toArray()[1];

        Long rR1Id = readRoom1.getId();
        Long rR2Id = readRoom2.getId();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        roomRepository.deleteById(rR1Id);
        roomRepository.deleteById(rR2Id);

    }

    @Test
    public void testFindRoomById() {

        //Given
        em = emFactory.createEntityManager();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        //When
        em.getTransaction().begin();
        Room readRoom = em.merge(room);
        Long id = readRoom.getId();
        em.getTransaction().commit();
        em.close();

        Room foundRoom = roomRepository.findById(id).orElse(new Room("No such room"));

        //Then
        Assert.assertEquals(readRoom, foundRoom);

        //CLeanUp
        roomRepository.deleteById(id);
    }

    @Test
    public void testSaveRoom() {
        //Given
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        roomRepository.save(room);

        //When
        Room roomSaved = (Room) roomRepository.findAll().toArray()[0];
        Long roomId = roomSaved.getId();
        String roomsName = roomSaved.getName();

        //Then
        Assert.assertEquals("Silence Service", roomsName);

        //CleanUp
        roomRepository.deleteById(roomId);

    }

    @Test
    public void testDeleteRoomById() {

        //Given
        em = emFactory.createEntityManager();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        //When
        int roomsNumberBefore = roomRepository.findAll().size();

        em.getTransaction().begin();
        Room readRoom = em.merge(room);
        Long id = readRoom.getId();
        em.getTransaction().commit();
        em.close();

        roomRepository.deleteById(id);

        int roomsNumberAfter = roomRepository.findAll().size();

        //Then
        Assert.assertEquals(1, roomsNumberBefore);
        Assert.assertEquals(0, roomsNumberAfter);
    }
}
