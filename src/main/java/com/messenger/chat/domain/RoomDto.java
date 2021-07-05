package com.messenger.chat.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RoomDto {
    private Long id;
    private String roomsName;
}
