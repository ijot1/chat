package com.messenger.chat.mapper;

import com.messenger.chat.domain.UserRoom;
import com.messenger.chat.domain.UserRoomDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserRoomMapper {
    public UserRoom mapToUserRoom(final UserRoomDto userRoomDto) {
        return UserRoom.builder()
                .id(userRoomDto.getUserRoomId())
                .addedOn(userRoomDto.getAddedOn())
                /*.user(userRoomDto.getUser())
                .room(userRoomDto.getRoom())*/
                .build();
    }

    public UserRoomDto mapToUserRoomDto(final UserRoom userRoom) {
        return UserRoomDto.builder()
                .userRoomId(userRoom.getId())
                .addedOn(userRoom.getAddedOn())
                /*.user(userRoom.getUser())
                .room(userRoom.getRoom())*/
                .build();
    }

    public List<UserRoomDto> mapToUserRoomDtoList(final List<UserRoom> userRoomList) {
        return userRoomList.stream()
                .map(this::mapToUserRoomDto)
                .collect(Collectors.toList());
    }

}
