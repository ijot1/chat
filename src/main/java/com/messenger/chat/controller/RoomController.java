package com.messenger.chat.controller;

import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.RoomDto;
import com.messenger.chat.mapper.RoomMapper;
import com.messenger.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/chat")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomMapper roomMapper;

    @GetMapping(value = "/rooms")
    public List<RoomDto> getRooms() {
        return roomMapper.mapToRoomDtoList(roomService.retrieveRooms());
    }

    @GetMapping(value = "/rooms/{roomId}")
    public RoomDto getRoom(@PathVariable Long roomId) {
        return roomMapper.mapToRoomDto(roomService.retrieveRoomById(roomId));
    }

    @PostMapping(value = "/rooms")
    public void createRoom(@RequestBody RoomDto roomDto) {
        roomService.saveRoom(roomMapper.mapToRoom(roomDto));
    }

    //updateRoom()

    @DeleteMapping(value = "/rooms/{roomId}")
    public void deleteRoom(@PathVariable Long roomId) {
        roomService.deleteRoomById(roomId);
    }
}
