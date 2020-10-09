package com.messenger.chat.repository;

import com.messenger.chat.domain.Message;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MessageRepositoryTestSuite {

    @Autowired
    MessageRepository messageRepository;

    @Test
    public void testSaveMessage() {

        //Given
        Message message = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .build();

        //When
        Message m = messageRepository.save(message);
        String str = m.getMessageText();
        Long id1 = m.getId();

        //Then
        Assert.assertEquals("Message #1 text", str);

        //CleanUp
        messageRepository.deleteById(id1);
    }

    @Test
    public void testGetAllMessages() {

        //Given
        Message message1 = Message.builder()
                .messageText("Message #1 text")
                .dateCreated(LocalDate.now())
                .build();

        Message message2 = Message.builder()
                .messageText("Message #2 text")
                .dateCreated(LocalDate.now())
                .build();
        //When
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
    }
}
