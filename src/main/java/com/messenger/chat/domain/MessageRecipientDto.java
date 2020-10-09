package com.messenger.chat.domain;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class MessageRecipientDto {
    private MessageRecipientId id;
}
