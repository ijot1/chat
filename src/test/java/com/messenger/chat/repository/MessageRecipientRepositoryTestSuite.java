package com.messenger.chat.repository;

import com.messenger.chat.domain.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
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

    @Before
    public void testPrepareMessageRecipientRepositoryTestSuite() {
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

        Room room = Room.builder()
                .id(null)
                .roomsName("Silence Service")
                .userRooms(new ArrayList<>())
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        roomRepository.save(room);
    }

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

        /*User user1 = User.builder()
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
                .build();*/

        MessageRecipient messageRecipient = MessageRecipient.builder()
                .addedBy("")
                .addedOn(LocalDate.now())
                .message(null)
                .userRoom(null)
                .user(null)
                .build();

        Room room = (Room)roomRepository.findAll().toArray()[0];

        User user1 = (User) userRepository.findAll().toArray()[0];
        User user2 = (User) userRepository.findAll().toArray()[1];

        user1.addMessage(message);

        message.setCreator(user1);

        messageRecipient.setUser(user2);
        messageRecipient.setMessage(message);
        messageRecipient.setAddedBy(user1.getNick());

        user1.addMessageRecipient(messageRecipient);
        user1.getFriends().add(user2);

        message.getMessageRecipientSet().add(messageRecipient);

        //When

        messageRepository.save(message);
        userRepository.save(user1);
        userRepository.save(user2);
        messageRecipientRepository.save(messageRecipient);

        Long recipientId = messageRecipient.getId();

        String addedByNick = messageRecipientRepository.findById(recipientId).orElse(new MessageRecipient("No one")).getAddedBy();

        //Then
        Assert.assertEquals("ij", addedByNick);

        //Clean up
        messageRecipientRepository.delete(messageRecipient);
        userRepository.delete(user1);
        userRepository.delete(user2);
        roomRepository.delete(room);
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
                .userRooms(new ArrayList<>())
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
