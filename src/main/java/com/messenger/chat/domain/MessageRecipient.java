package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Immutable  //?
@Table(name = "MESSAGE_RECIPIENTS")
public class MessageRecipient {
    @EmbeddedId
    private MessageRecipientId id = new MessageRecipientId();

    @Column(updatable = false)
    @NotNull
    private Date sentOn = new Date();

    @ManyToOne(
            optional = false,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "message_idmessage", nullable = false, insertable = false, updatable = false)
    private Message message;

    @ManyToOne(
            optional = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "room_idroom", nullable = false, insertable = false, updatable = false)
    private Room room;

    @ManyToOne(
            optional = true,
            fetch = FetchType.EAGER,
            cascade = CascadeType.ALL)
    @JoinColumn(name = "contact_idcontact", nullable = false, insertable = false, updatable = false)
    private User contact;
}
