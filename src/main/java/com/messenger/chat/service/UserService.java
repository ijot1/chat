package com.messenger.chat.service;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.domain.User;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Service
@EitherOrId
@EitherOr
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");


    public List<User> retrieveLoggedInUsers() {
        return  userRepository.findByLoggedInTrue();
    }

    public void addSendersFriend(final Long senderId, final Long friendId) {
        userRepository.addUsersFriend(senderId, friendId);
    }

    public void deleteSendersFriend(final Long userId, final Long friendId) {
        userRepository.deleteUsersFriend(userId, friendId);
    }

    public List<User> retrieveSendersFriends(final Long id) {
        return userRepository.findFriends(id);
    }

    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    public User retrieveUserById(final Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, id);

        if (user.getFriends() != null) {
            user.getFriends().clear();
        }
        if (user.getRecipients() != null) {
            user.getRecipients().clear();
        }
        if (user.getMessages() != null) {
            user.getMessages().clear();
        }
        if (user.getUserUsersRooms() != null) {
            user.getUserUsersRooms().clear();
        }

        /*Query q = em.createNativeQuery("select * from users u", User.class);
        List<User> users = q.getResultList();
        for (User u : users) {
            if (u.getFriends().contains(user)) {
                u.getFriends().remove(user);
            }
            if (u.getMessages() != null) {
                u.getMessages().clear();
            }
            if (u.getRecipients() != null) {
                u.getRecipients().clear();
            }
            if (u.getUserUsersRooms() != null) {
                u.getUserUsersRooms().clear();
            }
        }*/
        em.flush();
        em.getTransaction().commit();
        em.close();

        userRepository.deleteById(id);
    }
}
