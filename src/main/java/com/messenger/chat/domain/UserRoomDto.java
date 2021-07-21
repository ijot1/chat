package com.messenger.chat.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserRoomDto {
    private Long userId;
    private Long roomId;
    private LocalDate addedOn;
}
