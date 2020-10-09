package com.messenger.chat.controller;

import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserDto;
import com.messenger.chat.mapper.UserMapper;
import com.messenger.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/chat")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "getUsers")
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @GetMapping(value = "getLoggedInUsers")
    public List<UserDto> getLoggedInUsers() {
        return userService.getLoggedInUsers();
    }

    @GetMapping(value = "getUser")
    public UserDto getUser(@RequestParam Long userId) {
        return userService.getUserById(userId);
    }

    @DeleteMapping(value = "deleteUser")
    public void deleteUser(@RequestParam Long userId) {
        userService.delteUser(userId);
    }
}
