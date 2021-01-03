package com.messenger.chat.domain;


import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@Builder
public class MessageDto {
    private Long id;
    private String messageText;
    private LocalDate dateCreated;
}
