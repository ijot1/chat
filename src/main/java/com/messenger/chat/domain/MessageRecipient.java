package com.messenger.chat.domain;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
public class MessageRecipient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MESSAGE_RECIPIENT_ID")
    private Long id;

    private String addedBy;

    @Column(updatable = false)
    @NotNull
    private LocalDate addedOn;

    @ManyToOne(fetch = FetchType.LAZY/*, optional = false*/)    //EAGER
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;


    @ManyToOne(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
//    @JoinColumn(name = "MESSAGE_RECIPIENT_ID", insertable = false, updatable = false)
    @JoinColumns({
            @JoinColumn(name = "user", referencedColumnName = "USER_ID", insertable = false, updatable = false),
            @JoinColumn(name = "room", referencedColumnName = "ROOM_ID", insertable = false, updatable = false)
    })
    private UserRoom userRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Override
    public String toString() {
        return "MessageRecipient{" +
                "id=" + id +
                ", addedBy='" + addedBy + '\'' +
                ", addedOn=" + addedOn +
                ", message=" + message +
                ", userRoom=" + userRoom +
                ", user=" + user +
                '}';
    }

    public MessageRecipient(String addedBy) {
        this.addedBy = addedBy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MessageRecipient)) return false;
        MessageRecipient that = (MessageRecipient) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
