package com.messenger.chat.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
    private String name;

    @OneToMany(mappedBy = "room",
            cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    private Set<UserRoom> roomUsersRooms = new HashSet<>();

    public Room(String name) {
        this.name = name;
    }

    public void addUserRoomToRoom(User user) {
        UserRoom userRoom = new UserRoom(user, this);
        roomUsersRooms.add(userRoom);
        user.getUserUsersRooms().add(userRoom);
    }

    public void removeUserRoomFromRoom(User user) {
        Iterator<UserRoom> iterator = roomUsersRooms.iterator();
        while (iterator.hasNext()) {
            UserRoom ur = iterator.next();
            if (ur.getUser().equals(user) && ur.getRoom().equals(this)) {
                iterator.remove();
                ur.getUser().getUserUsersRooms().remove(ur);
                ur.setUser(null);
                ur.setRoom(null);
            }
        }
    }

    @Override
    public String toString() {
        return "Room{" +
                "id=" + id +
                ", roomsName='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Room)) return false;

        Room room = (Room) o;
        return id != null && id.equals(room.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
