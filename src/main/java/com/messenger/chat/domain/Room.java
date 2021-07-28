package com.messenger.chat.domain;

import lombok.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NaturalId;
import org.hibernate.annotations.NaturalIdCache;
import org.hibernate.annotations.SelectBeforeUpdate;

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
    private String name;

    @OneToMany(mappedBy = "room",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private Set<UserRoom> roomUsersRooms = new HashSet<>();

    public Room(String name) {
        this.name = name;
    }

    public void addUserRoomToRoom(User user) {
        UserRoom userRoom = new UserRoom(user, this);
        if (!roomUsersRooms.contains(userRoom)) {
            roomUsersRooms.add(userRoom);
            user.getUserUsersRooms().add(userRoom);
        }
    }

    public void removeUserRoomFromRoom(User user) {
        Iterator<UserRoom> iterator = roomUsersRooms.iterator();
        while (iterator.hasNext()) {
            UserRoom ur = iterator.next();
            if (ur.getUser().equals(user) && ur.getRoom().equals(this) ) {
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
//                ", userRoomSet=" + userRoomSet +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || !(o instanceof Room)) return false;

        final Room room = (Room) o;
            return Objects.equals(getName(), room.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
