package com.messenger.chat.controller;

import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.RoomDto;
import com.messenger.chat.mapper.RoomMapper;
import com.messenger.chat.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/chat")
public class RoomController {
    @Autowired
    private RoomService roomService;
    @Autowired
    private RoomMapper roomMapper;

    @GetMapping(value = "getRooms")
    public List<RoomDto> getRooms() {
        return roomService.getRooms();
    }

    @GetMapping(value = "getRoom")
    public RoomDto getRoom(@RequestParam Long roomId) {
        return roomService.getRoomById(roomId);
    }

    @DeleteMapping(value = "deleteRoom")
    public void deleteRoom(@RequestParam Long roomId) {
        roomService.deleteRoom(roomId);
    }
}
