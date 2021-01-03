package com.messenger.chat.repository;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.HashSet;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTestSuite {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void testSaveMessage() {

        //Given
        User user = User.builder()
                .nick("ijot")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        Message message = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .messageRecipientSet(new HashSet<>())
                .build();

        user.addMessage(message);

        //When
        Long uId = userRepository.save(user).getId();
        System.out.println("userId " + uId);

        Message m = messageRepository.save(message);
        String str = m.getMessageText();
        Long id1 = m.getId();

        //Then
        Assert.assertEquals("Message #1 text", str);

        //CleanUp
        messageRepository.deleteById(id1);
        userRepository.deleteById(uId);;
    }

    @Test
    public void testGetAllMessages() {

        //Given
        User user = User.builder()
                .id(null)
                .nick("ijot")
                .name("Irena-Janik")
                .sex('W')
                .location("Bangalore")
                .password("Zaq12wsx")
                .loggedIn(true)
                .messages(new HashSet<>())
                .messageRecipients(new HashSet<>())
                .friends(new HashSet<>())
                .friendshipOwners(new HashSet<>())
                .build();

        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .messageRecipientSet(new HashSet<>())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .creator(user)
                .messageRecipientSet(new HashSet<>())
                .build();

        user.addMessage(message1);
        user.addMessage(message2);
        //When
        Long uId = userRepository.save(user).getId();
        System.out.println("userId " + uId);

        Message m1 = messageRepository.save(message1);
        Message m2 = messageRepository.save(message2);
        int count = messageRepository.findAll().size();
        Long id1 = message1.getId();
        Long id2 = message2.getId();

        //Then
        Assert.assertEquals(2, count);

        //CleanUp
        messageRepository.deleteById(id1);
        messageRepository.deleteById(id2);
        userRepository.deleteById(uId);
    }
}
