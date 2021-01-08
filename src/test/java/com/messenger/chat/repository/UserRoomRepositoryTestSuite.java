package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
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
    MessageRecipientRepository messageRecipientRepository;

    @Before
    public void testPrepareUsersAndRooms() {
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
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

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
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

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
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        Room room = Room.builder()
                .id(null)
                .roomsName("Silence Service")
                .userRooms(new ArrayList<>())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        roomRepository.save(room);
    }

    @Test
    public void testGetAllUsersRooms() {
        //Given
        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        Room room = (Room) roomRepository.findAll().toArray()[0];

        room.addUserRoom(user3);

        UserRoom userRoom1 = new UserRoom(user1, room);
        UserRoom userRoom2 = new UserRoom(user2, room);

        //When
        roomRepository.save(room);

        userRoomRepository.save(userRoom1);
        userRoomRepository.save(userRoom2);

        int count = userRoomRepository.findAll().size();

        Long idx = room.getId();

        //Then
        Assert.assertEquals(3, count);

        //Clean up
        try {
            roomRepository.deleteById(idx);
            userRepository.delete(user1);
            userRepository.delete(user2);
            userRepository.delete(user3);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testGetUserRoomById() {
        //Given
        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        Room room = (Room) roomRepository.findAll().toArray()[0];
        Long idx = room.getId();

        room.addUserRoom(user1);
        UserRoom savedUserRoom = new UserRoom(user1, room);

        //When
        userRepository.save(user1);
        userRoomRepository.save(savedUserRoom);
        UserRoom readUserRoom = userRoomRepository.findById(savedUserRoom.getUserRoomId()).orElse(null);

        //Then
        Assert.assertEquals(savedUserRoom, readUserRoom);

        //Clean up
        try {
            roomRepository.deleteById(idx);
            userRepository.delete(user1);
            userRepository.delete(user2);
            userRepository.delete(user3);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testSaveUserRoom() {
        //Given
        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        Room room = (Room) roomRepository.findAll().toArray()[0];
        Long idx = room.getId();

        room.addUserRoom(user1);
        UserRoom toSaveUserRoom = new UserRoom(user1, room);

        //When
        userRepository.save(user1);
        UserRoom savedUserRoom = userRoomRepository.save(toSaveUserRoom);
        UserRoom readUserRoom = userRoomRepository.findById(savedUserRoom.getUserRoomId()).orElse(null);

        //Then
        Assert.assertEquals(toSaveUserRoom, readUserRoom);

        //Clean up
        try {
            roomRepository.deleteById(idx);
            userRepository.delete(user1);
            userRepository.delete(user2);
            userRepository.delete(user3);
        } catch (Exception e) {
            //
        }
    }

    @Test
    public void testDeleteUserRoom() {
        //Given
        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];
        User user3 = (User) userRepository.findAll().toArray()[2];

        Room room = (Room) roomRepository.findAll().toArray()[0];

        room.addUserRoom(user3);

        UserRoom userRoom1 = new UserRoom(user1, room);
        UserRoom userRoom2 = new UserRoom(user2, room);

        //When
        roomRepository.save(room);

        userRoomRepository.save(userRoom1);
        userRoomRepository.save(userRoom2);

        userRoomRepository.delete(userRoom2);

        int count = userRoomRepository.findAll().size();

        Long idx = room.getId();

        //Then
        Assert.assertEquals(2, count);

        //Clean up
        try {
            roomRepository.deleteById(idx);
            userRepository.delete(user1);
            userRepository.delete(user2);
            userRepository.delete(user3);
        } catch (Exception e) {
            //
        }
    }
}
