package com.messenger.chat.service;

import com.messenger.chat.domain.User;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.domain.Message;
import com.messenger.chat.repository.MessageRepository;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;
import java.util.List;

@Service
public class MessageService {
    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");


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
        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        Message message = em.find(Message.class, id);
        User creator = em.find(User.class, message.getCreator().getId());
        creator.removeMessage(message);

        em.refresh(creator);
        em.flush();
        em.getTransaction().commit();
        em.close();

        messageRepository.deleteById(id);
    }
}
