package com.messenger.chat.domain;


import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageDto {
    private Long id;
    private String messageText;
    private LocalDate dateCreated;
}
