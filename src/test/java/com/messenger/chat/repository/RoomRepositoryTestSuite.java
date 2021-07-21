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
        em = emFactory.createEntityManager();

        em.getTransaction().begin();
        Room room = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        em.persist(room);
        em.getTransaction().commit();

        em.getTransaction().begin();
        User user = User.builder()
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

        em.persist(user);
        em.getTransaction().commit();

        //When
        em.getTransaction().begin();
        User userSaved = em.merge(user);
        Room roomSaved = em.merge(room);
        roomSaved.addUserRoomToRoom(userSaved);
        em.getTransaction().commit();
        em.close();

        String roomsName = roomSaved.getName();
        UserRoom ur = (UserRoom) roomSaved.getRoomUsersRooms().toArray()[0];
        String userRoomsUserName = ur.getUser().getName();
        Long id1 = roomSaved.getId();
        Long id2 = userSaved.getId();

        //Then
        Assert.assertEquals("Silence Service", roomsName);
        Assert.assertEquals("Irena-Janik", userRoomsUserName);

        //CleanUp
        roomRepository.deleteById(id1);
        userRepository.deleteById(id2);
    }

    @Test
    public void testFindRooms() {

        //Given
        Room room1 = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        Room room2 = Room.builder()
                .id(null)
                .name("Java Talks")
                .roomUsersRooms(new HashSet<>())
                .build();

        //When
        roomRepository.save(room1);
        roomRepository.save(room2);
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
        Room room1 = Room.builder()
                .id(null)
                .name("Silence Service")
                .roomUsersRooms(new HashSet<>())
                .build();

        Room room2 = Room.builder()
                .id(null)
                .name("Java Talks")
                .roomUsersRooms(new HashSet<>())
                .build();

        //When
        roomRepository.save(room1);
        roomRepository.save(room2);
        int count = roomRepository.findAll().size();
        Long id1 = room1.getId();
        Long id2 = room2.getId();
        Room readRoom = roomRepository.findById(id2).orElse(new Room("No such room"));

        //Then
        Assert.assertEquals(room2, readRoom);

        //CleanUp
        roomRepository.deleteById(id1);
        roomRepository.deleteById(id2);
    }
}
