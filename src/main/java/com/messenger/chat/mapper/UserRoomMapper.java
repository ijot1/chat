package com.messenger.chat.mapper;

import com.messenger.chat.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoomMapper {
    public UserRoom mapToUserRoom(final UserRoomDto userRoomDto, final User user, final Room room) {
        return UserRoom.builder()
                .id(new UserRoomId(userRoomDto.getUserId(), userRoomDto.getRoomId()))
                .user(user)
                .room(room)
                .addedOn(userRoomDto.getAddedOn())
                .build();
    }

    public UserRoomDto mapToUserRoomDto(final UserRoom userRoom) {
        return UserRoomDto.builder()
                .userId(userRoom.getId().getUserId())
                .roomId(userRoom.getId().getRoomId())
                .addedOn(userRoom.getAddedOn())
                .build();
    }

    public List<UserRoomDto> mapToUserRoomDtoList(final List<UserRoom> userRoomList) {
        return userRoomList.stream()
                .map(this::mapToUserRoomDto)
                .collect(Collectors.toList());
    }

}
