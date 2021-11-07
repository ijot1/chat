package com.messenger.chat.controller;

import com.messenger.chat.domain.UserRoomDto;
import com.messenger.chat.mapper.UserRoomMapper;
import com.messenger.chat.service.UserRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/chat")
public class UserRoomController {
    @Autowired
    private UserRoomService userRoomService;
    @Autowired
    private UserRoomMapper userRoomMapper;

    @GetMapping(value = "/usersRooms")
    public List<UserRoomDto> getUsersRooms() {
        return userRoomMapper.mapToUserRoomDtoList(userRoomService.retrieveUsersRooms());
    }

    @GetMapping(value = "/usersRooms/{userId}/{roomId}")
    public UserRoomDto getUserRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        return userRoomMapper.mapToUserRoomDto(userRoomService.retrieveUserRoom(userId, roomId));
    }

    @GetMapping(value = "/usersRooms/{roomId}")
    public List<UserRoomDto> getRoomsMembers(@PathVariable Long roomId) {
        return userRoomMapper.mapToUserRoomDtoList(userRoomService.retrieveUsersRoomsByRoomId(roomId));
    }

    @PostMapping(value = "/usersRooms", consumes = APPLICATION_JSON_VALUE)
    public void createUserRoom(@RequestBody UserRoomDto userRoomDto) {
        userRoomService.create(userRoomDto);
    }

    /*@DeleteMapping(value = "/usersRooms", consumes = APPLICATION_JSON_VALUE)
    public void deleteUserRoom(@RequestBody UserRoomDto userRoomDto) {
        userRoomService.deleteUserRoomByBody(userRoomDto);
    }*/

    @DeleteMapping(value = "/usersRooms/{userId}/{roomId}", consumes = APPLICATION_JSON_VALUE)
    public void deleteUserRoom(@PathVariable Long userId, @PathVariable Long roomId) {
        userRoomService.removeUserRoom(userId, roomId);
    }
}
