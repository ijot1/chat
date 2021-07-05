package com.messenger.chat.service;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.domain.EntityNotFoundException;
import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserDto;
import com.messenger.chat.mapper.UserMapper;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@EitherOrId
@EitherOr
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> retrieveLoggedInUsers() {
        return  userRepository.findByLoggedInTrue();
    }

    public List<User> addSendersFriend(final Long userId, final Long friendId) {
        userRepository.findFriends(userId).add(userRepository.findById(friendId).orElse(null));
        return userRepository.findFriends(userId);
    }

    public List<User> retrieveSendersFriends(final Long id) {
        return userRepository.findFriends(id);
    }

    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    public User retrieveUserById(final Long id) throws EntityNotFoundException {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
