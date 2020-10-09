package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "ROOMS")
public class Room implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ROOM_ID")
    private Long id;

    @Column(name = "NAME")
    private String roomsName;

    @ManyToMany(mappedBy = "rooms")
    private Set<User> users = new HashSet<>();

    @OneToMany(mappedBy = "room")
    private Set<MessageRecipient> messageRecipientSet = new HashSet<>();

    public void addUser(User user) {
        users.add(user);
        user.getRooms().add(this);
    }

    public void removeUser(User user) {
        users.remove(user);
        user.getRooms().remove(this);
    }

    public void addMessageRecipient(MessageRecipient messageRecipient) {
        messageRecipientSet.add(messageRecipient);
        messageRecipient.setRoom(this);
    }

    public void deleteMessageRecipient(MessageRecipient messageRecipient) {
        messageRecipientSet.remove(messageRecipient);
        messageRecipient.setRoom(null);
    }

}
