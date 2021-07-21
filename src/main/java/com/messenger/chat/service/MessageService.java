package com.messenger.chat.service;

import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.domain.Message;
import com.messenger.chat.repository.MessageRepository;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Message> retrieveMessages() {
        return messageRepository.findAll();
    }

    public Message retrieveMessageById(Long id) throws EntityNotFoundException {
        return messageRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Message.class, id));
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public void deleteMessageById(Long id) {
        messageRepository.deleteById(id);
    }
}
