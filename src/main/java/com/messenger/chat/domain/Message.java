package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "MESSAGES")
public class Message implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "MESSAGE_TXT")
    private String messageText;

    @Column(name = "CREATED", nullable = false)
    private LocalDate dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    private User messageCreator;

    @OneToMany(mappedBy = "message")
    private Set<MessageRecipient> messageRecipientSet = new HashSet<>();

    public void addMessageRecipient(MessageRecipient messageRecipient) {
        messageRecipientSet.add(messageRecipient);
        messageRecipient.setMessage(this);
    }

    public void deleteMessageRecipient(MessageRecipient messageRecipient) {
        messageRecipientSet.remove(messageRecipient);
        messageRecipient.setMessage(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return getId().equals(message.getId()) &&
                getMessageText().equals(message.getMessageText()) &&
                getDateCreated().equals(message.getDateCreated()) &&
                getMessageCreator().equals(message.getMessageCreator());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getMessageText(), getDateCreated(), getMessageCreator());
    }
}
