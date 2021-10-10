package com.messenger.chat.domain;

import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class UserRoomDto implements Serializable {
    private Long userId;
    private Long roomId;
    private LocalDate addedOn;
}
