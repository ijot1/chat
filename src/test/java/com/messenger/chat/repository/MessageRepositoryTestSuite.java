package com.messenger.chat.repository;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MessageRepositoryTestSuite {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void testFindAllMessages() {

        //Given
        User user = User.builder()
                .id(null)
                .nick("ijot")
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

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        user.addMessage(message1);
        user.addMessage(message2);
        //When
        messageRepository.save(message1);
        messageRepository.save(message2);
        int count = messageRepository.findAll().size();

        //Then
        Assert.assertEquals(2, count);

    }

    @Test
    public void testFindMessageById() {

        //Given
        User user = User.builder()
                .id(null)
                .nick("ijot")
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

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        user.addMessage(message1);
        user.addMessage(message2);
        //When
        messageRepository.save(message1);
        Message readMessage = messageRepository.findById(message2.getId()).orElse(new Message("No such Message"));

        //Then
        Assert.assertEquals(message2, readMessage);

    }

    @Test
    public void testSaveMessage() {

        //Given
        User user = User.builder()
                .nick("ijot")
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

        Message message = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        user.addMessage(message);

        //When
        String str = messageRepository.save(message).getMessageText();

        //Then
        Assert.assertEquals("Message #1 text", str);

    }

    @Test
    public void testDeleteMessageById() {

        //Given
        User user = User.builder()
                .id(null)
                .nick("ijot")
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

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .recipientSet(new HashSet<>())
                .build();

        //When
        messageRepository.save(message1);
        Long mId = messageRepository.save(message2).getId();
        messageRepository.deleteById(mId);
        int count = messageRepository.findAll().size();

        //Then
        Assert.assertEquals(1, count);

    }
}
