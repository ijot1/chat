package com.messenger.chat.repository;

import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserRoom;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
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
public class RoomRepositoryTestSuite {

    @Autowired
    public RoomRepository roomRepository;

    @Autowired
    public UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");

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
        String roomsName = roomSaved.getName();
        Long id = roomSaved.getId();

        //Then
        Assert.assertEquals("Silence Service", roomsName);

        //CleanUp
        roomRepository.deleteById(id);
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
        Long id1 = room1.getId();
        Long id2 = room2.getId();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        roomRepository.deleteById(id1);
        roomRepository.deleteById(id2);
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

        //CleanUp
        roomRepository.deleteById(id);
    }
}
