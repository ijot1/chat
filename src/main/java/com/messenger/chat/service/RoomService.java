package com.messenger.chat.service;

import com.messenger.chat.domain.EntityNotFoundException;
import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.RoomDto;
import com.messenger.chat.mapper.RoomMapper;
import com.messenger.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoomService {
    private RoomRepository roomRepository;
    private RoomMapper roomMapper;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomMapper roomMapper) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
    }

    public List<RoomDto> getRooms() {
        return roomMapper.mapToRoomDtoList(roomRepository.findAll());
    }

    public RoomDto getRoomById(final Long id) {
        return roomMapper.mapToRoomDto(roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Room.class, id)));
    }

    public void deleteRoom(Long id) {
        roomRepository.deleteById(id);
    }
}
