package com.messenger.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "ROOMS")
public class Room implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ROOM_ID")
    private Long id;

    @Column(name = "ROOMS_NAME")
    private String roomsName;

    @OneToMany(mappedBy = "room", cascade = CascadeType.MERGE, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<UserRoom> userRooms = new ArrayList<>();

    public void addUserRoom(User user) {
        if (user != null) {
            UserRoom userRoom = new UserRoom(user, this);
            if (getUserRooms().contains(userRoom)) {
                getUserRooms().set(getUserRooms().indexOf(userRoom), userRoom);
            } else {
                userRooms.add(userRoom);
            }
        }
    }

    public Room(String roomsName) {
        this.roomsName = roomsName;
    }

    public void removeUserRoom(UserRoom userRoom) {
        Iterator<UserRoom> iterator = userRooms.iterator();
        while (iterator.hasNext()) {
            UserRoom ur = (UserRoom) iterator.next();
            if (ur.equals(userRoom) && ur.getRoom().equals(this))
                iterator.remove();
            ur.setUser(null);
            ur.setRoom(null);
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomsName='" + roomsName + '\'' +
                ", userRooms=" + userRooms +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Room)) return false;

        final Room room = (Room) o;
        return Objects.equals(roomsName, room.roomsName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(roomsName);
    }
}
