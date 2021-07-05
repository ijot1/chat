package com.messenger.chat.mapper;

import com.messenger.chat.domain.Room;
import com.messenger.chat.domain.RoomDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoomMapper {
    public Room mapToRoom(final RoomDto roomDto) {
        return Room.builder()
                .id(roomDto.getId())
                .name(roomDto.getRoomsName())
                .build();
    }

    public RoomDto mapToRoomDto(final Room room) {
        return RoomDto.builder()
                .id(room.getId())
                .roomsName(room.getName())
                .build();
    }

    public List<RoomDto> mapToRoomDtoList(List<Room> roomList) {
        return roomList.stream()
                .map(this::mapToRoomDto)
                .collect(Collectors.toList());
    }
}
