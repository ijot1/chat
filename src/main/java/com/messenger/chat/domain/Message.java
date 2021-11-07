package com.messenger.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import org.springframework.lang.Nullable;

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
//@JsonIgnoreProperties(value = {"recipientSet"})
@Table(name = "MESSAGES")
public class Message implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MESSAGE_ID")
    private Long id;

    @Column(name = "MESSAGE_TXT")
    private String messageText;

    @Column(name = "CREATED_ON")
    private LocalDate dateCreated;

    @ManyToOne(fetch = FetchType.LAZY)
    @Nullable
    @JoinColumn(name = "CREATED_BY")
    private User creator;

    @OneToMany(mappedBy = "message",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE},
            fetch = FetchType.LAZY)
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
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Message)) return false;
        Message message = (Message) o;
        return id != null && id.equals(message.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageText, dateCreated, creator);
    }
}
