package com.messenger.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "MESSAGES")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "MESSAGE_TXT")
    private String messageText;

    @Column(name = "CREATED_ON"/*, nullable = false*/)
    private LocalDate dateCreated;

    @ManyToOne(targetEntity = User.class,
            cascade = CascadeType.PERSIST,     //MERGE
            fetch = FetchType.LAZY)            //default for ToOne EAGER
    @JoinColumn(name = "CREATED_BY")
    private User creator;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true) //EAGER
    @JoinColumn(name = "MESSAGE_ID",
            /*nullable = false,*/ insertable = false, updatable = false
    )
    private Set<MessageRecipient> messageRecipientSet = new HashSet<>();

    public Message(String messageText) {
        this.messageText = messageText;
    }

    public void addMessageRecipient(MessageRecipient messageRecipient) {
        this.messageRecipientSet.add(messageRecipient);
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
        return id.equals(message.id) &&
                messageText.equals(message.messageText) &&
                dateCreated.equals(message.dateCreated) &&
                creator.equals(message.creator);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, messageText, dateCreated, creator);
    }
}
