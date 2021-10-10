package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Embeddable
public class UserRoomId implements Serializable {

    @Column(name = "USER_ID")
    protected Long userId;

    @Column(name = "ROOM_ID")
    protected Long roomId;

    @Override
    public String toString() {
        return "UserRoomId{" +
                "userId=" + userId +
                ", roomId=" + roomId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserRoomId)) return false;
        UserRoomId that = (UserRoomId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

