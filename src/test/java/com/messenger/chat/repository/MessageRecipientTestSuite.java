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
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRecipientTestSuite {

    @Autowired
    MessageRecipientRepository messageRecipientRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void testGetMessageRecipients() {
        //Given
        Room room = Room.builder()
                .id(null)
                .roomsName("Silence Service")
                .users(new HashSet<>())
                .messageRecipientSet(new HashSet<>())
                .build();

        User user1 = User.builder()
                .id(null)
                .nick("ij")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messageRecipients(new HashSet<>())
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        System.out.println("### user1 " + user1);

        User user2 = User.builder()
                .id(null)
                .nick("jk")
                .name("Janina-Kranik")
                .sex('W')
                .location("Bialsk Podlaski")
                .createdOn(LocalDate.now())
                .password("Zaq12wsx")
                .loggedIn(true)
                .messageRecipients(new HashSet<>())
                .messages(new ArrayList<>())
                .rooms(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .friends(new HashSet<>())
                .build();

        System.out.println("### user2 " + user2);

        Message message = Message.builder()
                .id(null)
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .messageCreator(user1)
                .messageRecipientSet(new HashSet<>())
                .build();

        System.out.println("### message " + message);

        MessageRecipientId messageRecipientId = new MessageRecipientId();

        MessageRecipient messageRecipient = MessageRecipient.builder()
                .id(new MessageRecipientId())
                .message(message)
                .room(null)
                .contact(user2)
                .build();


        messageRecipient.setMessage(message);
        message.addMessageRecipient(messageRecipient);

        System.out.println("##### " + messageRecipientId);

        System.out.println("### messageRecipient " + messageRecipient);

        user1.getMessages().add(message);

       /* room.addUser(user1);
        room.addUser(user2);

        room.getMessageRecipientSet().add(messageRecipient);*/

        //When
        userRepository.save(user1);
        userRepository.save(user2);

        messageRepository.save(message);
//        roomRepository.save(room);

        messageRecipientRepository.save(messageRecipient);

        int count = messageRecipientRepository.findAll().size();

        //Then
        Assert.assertEquals(1, count);


        //Clean up
    }
}
