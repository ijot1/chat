package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRecipientRepositoryTestSuite {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    MessageRecipientRepository messageRecipientRepository;

    @Autowired
    UserRoomRepository userRoomRepository;

    @Test
    public void testGetMessageRecipientUser() {
        //Given
        Message message = Message.builder()
                .id(null)
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(null)
                .messageRecipientSet(new HashSet<>())
                .build();

        System.out.println("messageId " + message.getId());

        User user1 = User.builder()
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
//                .userRoomId(null)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        System.out.println("user1Id " + user1.getId());

        User user2 = User.builder()
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
//                .userRoomId(null)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        System.out.println("user2Id " + user2.getId());

        MessageRecipient messageRecipient = MessageRecipient.builder()
                .addedBy("")
                .addedOn(LocalDate.now())
                .message(message)
                .userRoom(null)
                .user(null)
                .build();

        user1.addMessage(message);
        user1.addMessageRecipient(messageRecipient);
        user1.addFriend(user2);

        message.setCreator(user1);
        message.getMessageRecipientSet().add(messageRecipient);

        messageRecipient.setMessage(message);
        messageRecipient.setUser(user2);
        messageRecipient.setAddedBy(user1.getNick());

        //When

        userRepository.save(user1);
        userRepository.save(user2);
        messageRepository.save(message);
        messageRecipientRepository.save(messageRecipient);

        int count = messageRecipientRepository.findAll().size();

        //Then
        Assert.assertEquals(1, count);

        //Clean up
//        messageRecipientRepository.delete(messageRecipient);
    }

    @Test
    public void testGetMessageRecipientGroup() {
        //Given
        Message message = Message.builder()
                .id(null)
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(null)
                .messageRecipientSet(new HashSet<>())
                .build();

        System.out.println("messageId " + message.getId());

        User user1 = User.builder()
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
//                .userRooms(null)
                .build();


        /*MessageRecipient messageRecipient = MessageRecipient.builder()
                .addedBy("")
                .addedOn(LocalDate.now())
                .message(message)
                .userRooms(new HashSet<>())
                .user(null)
                .build();*/


//        message.getMessageRecipientSet().add(messageRecipient);

        user3.addMessage(message);

        user3.addFriend(user2);
        user3.addFriend(user1);

       /* MessageRecipient [] messageRecipients = userR.getUserRooms().toArray(new UserRoom[0]);
        userRooms[0].getMessageRecipient().setMessage(message);
        userRooms[1].getMessageRecipient().setMessage(message);
//        userRooms[2].getMessageRecipient().setMessage(message);

        UserRoom userRoom1 = userRooms[0];
        UserRoom userRoom2 = userRooms[1];
        UserRoom userRoom3 = userRooms[2];

        room.addUserRoom(user1);
        room.addUserRoom(user2);
        room.addUserRoom(user3);

        System.out.println("userRoom1 " + userRoom1.toString());
        System.out.println("userRoom2 " + userRoom1.toString());
        System.out.println("userRoom3 " + userRoom1.toString());

        MessageRecipient messageRecipient1 = userRooms[0].getMessageRecipient();
        MessageRecipient messageRecipient2 = userRooms[1].getMessageRecipient();

        messageRecipient1.addUserRoom(room);
        messageRecipient2.addUserRoom(room);

        message.setCreator(user3);
        message.addMessageRecipient(messageRecipient1);
        message.addMessageRecipient(messageRecipient2);

        user3.addMessageRecipient(messageRecipient1);
        user3.addMessageRecipient(messageRecipient2);


        //When
        messageRepository.save(message);
        roomRepository.save(room);

        userRepository.save(user1);
        userRepository.save(user2);
        userRepository.save(user3);

        messageRecipientRepository.save(messageRecipient1);
        messageRecipientRepository.save(messageRecipient2);

        userRoomRepository.save(userRoom1);
        userRoomRepository.save(userRoom2);
        userRoomRepository.save(userRoom3);

//        int count = messageRecipientRepository.findAll().size();
        MessageRecipient [] recipients = messageRecipientRepository.findAll().toArray(new MessageRecipient[0]);
        int count = recipients[0].getUserRooms().size();*/

        //Then
//        Assert.assertEquals(2, count);

        //Clean up
//        messageRecipientRepository.delete(messageRecipient);
    }
}
