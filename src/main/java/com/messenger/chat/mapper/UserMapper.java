package com.messenger.chat.mapper;

import com.messenger.chat.domain.User;
import com.messenger.chat.domain.UserDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {
    public  User mapToUser(final UserDto userDto) {
        return User.builder()
                .id(userDto.getId())
                .nick(userDto.getNick())
                .name(userDto.getName())
                .sex(userDto.getSex())
                .location(userDto.getLocation())
                .password(userDto.getPassword())
                .loggedIn(userDto.isLoggedIn())
                .build();
    }

    public UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .nick(user.getNick())
                .name(user.getName())
                .sex(user.getSex())
                .location(user.getLocation())
                .password(user.getPassword())
                .loggedIn(user.isLoggedIn())
                .build();
    }

    public List<UserDto> mapToUserDtoList(final List<User> userList) {
        return userList.stream()
                .map(this::mapToUserDto)
                .collect(Collectors.toList());
    }
}
