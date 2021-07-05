package com.messenger.chat.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserRoomDto {
    private UserRoomId userRoomId;
    private LocalDate addedOn;
}
