package com.messenger.chat.service;

import com.messenger.chat.domain.EntityNotFoundException;
import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserDto;
import com.messenger.chat.mapper.UserMapper;
import com.messenger.chat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private UserMapper userMapper;

    @Autowired
    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    public List<UserDto> getLoggedInUsers() {
        return userMapper.mapToUserDtoList(userRepository.findAll().stream()
                .filter(u -> u.isLoggedIn() == true)
                .collect(Collectors.toList()));
    }

    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userRepository.findAll());
    }

    public UserDto getUserById(final Long id) {
        return userMapper.mapToUserDto(userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(User.class, id)));
    }

    public void delteUser(Long id) {
        userRepository.deleteById(id);
    }
}
