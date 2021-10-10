package com.messenger.chat.service;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.domain.User;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
@EitherOrId
@EitherOr
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
        userRepository.deleteById(id);
    }
}
