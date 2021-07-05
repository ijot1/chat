package com.messenger.chat.service;

import com.messenger.chat.domain.EntityNotFoundException;
import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.RoomDto;
import com.messenger.chat.mapper.RoomMapper;
import com.messenger.chat.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {
    @Autowired
    private RoomRepository roomRepository;

    public List<Room> retrieveRooms() {
        return roomRepository.findAll();
    }

    public Room retrieveRoomById(final Long id) throws EntityNotFoundException {
        return roomRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Room.class, id));
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }
}
