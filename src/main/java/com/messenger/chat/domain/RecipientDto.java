package com.messenger.chat.domain;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@EitherOrId
public class RecipientDto {
    private Long id;
    private LocalDate addedOn;
    private Long messageId;
    private Long userRoomUserId;
    private Long userRoomRoomId;
    private Long userId;
}
