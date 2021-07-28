package com.messenger.chat.controller;

import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserDto;
import com.messenger.chat.mapper.UserMapper;
import com.messenger.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import sun.management.snmp.jvminstr.JvmOSImpl;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/chat")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    @GetMapping(value = "/users")
    public List<UserDto> getUsers() {
        return userMapper.mapToUserDtoList(userService.retrieveUsers());
    }

    @GetMapping(value = "/users/loggedIn")
    public List<UserDto> getLoggedInUsers() {
        return userMapper.mapToUserDtoList(userService.retrieveLoggedInUsers());
    }

    @GetMapping(value = "/users/friends/{senderId}")
    public List<UserDto> getFriends(@PathVariable Long senderId) {
        return userMapper.mapToUserDtoList(userService.retrieveSendersFriends(senderId)); }

    @GetMapping(value = "/users/{userId}")
    public UserDto getUser(@PathVariable Long userId) {
        return userMapper.mapToUserDto(userService.retrieveUserById(userId));
    }

    @PutMapping(value = "/users/friends/{userId}/{friendId}")
    public void addFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.addSendersFriend(userId, friendId);
    }

    @DeleteMapping(value = "/users/friends/{userId}/{friendId}")
    public void deleteFriend(@PathVariable Long userId, @PathVariable Long friendId) {
        userService.deleteSendersFriend(userId, friendId);
    }

    @PutMapping(value = "/users/{userId}")
    public User updateUser(@PathVariable Long userId, @RequestBody UserDto userDto) {
        User persistedUser = userService.retrieveUserById(userId);
        if (persistedUser != null) {
            persistedUser.setId(userId);
            persistedUser.setNick(userDto.getNick());
            persistedUser.setName(userDto.getName());
            persistedUser.setSex(userDto.getSex());
            persistedUser.setLocation(userDto.getLocation());
            persistedUser.setCreatedOn(userDto.getCreatedOn());
            persistedUser.setPassword(userDto.getPassword());
            persistedUser.setLoggedIn(userDto.isLoggedIn());
        }
        return userService.saveUser(persistedUser);
    }

    @PostMapping(value = "/users", consumes = APPLICATION_JSON_VALUE)
    public void createUser(@RequestBody UserDto userDto) {
        userService.saveUser(userMapper.mapToUser(userDto));
    }

    @DeleteMapping(value = "/users/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.deleteUserById(userId);
    }
}
