package com.messenger.chat.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
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
            cascade = CascadeType.PERSIST,
            fetch = FetchType.LAZY)            //default for ToOne EAGER
    @NotNull
    @JoinColumn(name = "CREATED_BY")
    private User creator;

    @OneToMany(mappedBy = "message", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    /*@JoinColumn(name = "MESSAGE_ID",    //"MESSAGE_ID"
            insertable = false,
            updatable = false
    )*/
    private Set<Recipient> recipientSet;

    public Message(String messageText) {
        this.messageText = messageText;
    }

    public void setCreator(User creator) {
        setCreator(creator, true);
    }

    void setCreator(User creator, boolean add) {
        this.creator = creator;
        if (creator != null && add) {
            creator.addMessage(this, false);
        }
    }

    public void addMessageRecipient(Recipient recipient) {
        this.recipientSet.add(recipient);
        recipient.setMessage(this);
    }

    public void deleteMessageRecipient(Recipient recipient) {
        recipientSet.remove(recipient);
        recipient.setMessage(null);
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", messageText='" + messageText + '\'' +
                ", dateCreated=" + dateCreated +
                ", creator=" + creator +
                ", recipientSet=" + recipientSet +
                '}';
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
