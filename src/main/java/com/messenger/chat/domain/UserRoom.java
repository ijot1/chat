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
@Entity(name = "UserRoom")
@NamedNativeQuery(
        name = "UserRoom.findByRoomId",
        query = "select ur.* from users_rooms ur where ur.room_id = ?",
        resultClass = UserRoom.class)
@Table(name = "USERS_ROOMS")
public class UserRoom implements Serializable {

    @EmbeddedId
    private UserRoomId id;

    @MapsId("USER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", insertable = false, updatable = false)
    private User user;

    @MapsId("ROOM_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROOM_ID", insertable = false, updatable = false)
    private Room room;

    @Column(name = "ADDED_ON", updatable = false)
    private LocalDate addedOn;

    @OneToMany(mappedBy = "userRoom",
            cascade = CascadeType.PERSIST)
    private Set<Recipient> recipients;

    public UserRoom(User user, Room room) {
        this.user = user;
        this.room = room;
        this.addedOn = LocalDate.now();
        this.recipients = new HashSet<>();
        this.id = new UserRoomId(user.getId(), room.getId());
    }

    public void addMessageRecipient(Recipient recipient) {
        this.recipients.add(recipient);
        recipient.setUserRoom(this);
    }

    public void removeMessageRecipient(Recipient recipient) {
        this.recipients.remove(recipient);
        recipient.setUserRoom(null);
    }

    @Override
    public String toString() {
        return "UserRoom{" +
                "userRoomId=" + id +
                ", addedOn=" + addedOn +
                ", user=" + user +
                ", room=" + room +
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