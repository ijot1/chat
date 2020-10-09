package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class MessageRecipientId implements Serializable {

    @Column(name = "MESSAGE_ID")
    private Long messageId;

    @Column(name = "ROOM_ID")
    private Long roomId;

    @Column(name = "CONTACT_ID")
    private Long contactId;
}
