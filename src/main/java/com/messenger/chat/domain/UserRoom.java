package com.messenger.chat.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
@Table(name = "USERS_ROOMS")
public class UserRoom implements Serializable {

    @EmbeddedId
    private UserRoomId userRoomId = new UserRoomId();

    @Column(updatable = false)
    @NotNull
    private LocalDate addedOn;

    @MapsId("USER_ID")
    @ManyToOne   //default for ToOne is EAGER
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @MapsId("ROOM_ID")
    @ManyToOne(cascade = CascadeType.PERSIST/*, fetch = FetchType.LAZY*/)
    @JoinColumn(name = "ROOM_ID", insertable = false, updatable = false)
    private Room room;

    @OneToMany(mappedBy = "userRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageRecipient> messageRecipients;

    public UserRoom(User user, Room room) {
        this.addedOn = LocalDate.now();
        this.user = user;
        this.room = room;
        this.userRoomId.userId = user.getId();
        this.userRoomId.roomId = room.getId();
        this.messageRecipients = new HashSet<>();
    }

    public void addUMessageRecipient(UserRoom userRoom) {
        MessageRecipient messageRecipient = new MessageRecipient();
        messageRecipient.setUserRoom(this);
        this.messageRecipients.add(messageRecipient);
    }

    public void removeMessageRecipient(MessageRecipient messageRecipient) {
        this.messageRecipients.remove(messageRecipient);
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "userRoomId=" + userRoomId +
                ", addedOn=" + addedOn +
                ", user=" + user +
                ", room=" + room +
                ", messageRecipients=" + messageRecipients +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRoom userRoom = (UserRoom) o;
        return Objects.equals(user, userRoom.user) &&
                Objects.equals(room, userRoom.room);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, room);
    }


}
