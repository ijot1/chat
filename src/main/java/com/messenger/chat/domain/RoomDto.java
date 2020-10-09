package com.messenger.chat.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RoomDto {
    private Long id;
    private String roomsName;
}
