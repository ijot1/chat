package com.messenger.chat.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "USER_ID")
    private Long id;

    @Column(name = "NICK", nullable = false)
    private String nick;

    @Column(name = "NAME", nullable = false)
    private String name;

    @Column(name = "SEX")
    private char sex;

    @Column(name = "LOCATION")
    private String location;

    @Column(name = "CREATED_ON")
    private LocalDate createdOn;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LOGGED_IN")
    private boolean loggedIn;

    @OneToMany(
            mappedBy = "messageCreator",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<Message> messages = new ArrayList<>();

    @OneToMany(mappedBy = "contact")
    private Set<MessageRecipient> messageRecipients = new HashSet<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            })
    @JoinTable(name = "USERS_ROOMS",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROOM_ID")
    )
    private Set<Room> rooms = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<User> friends = new HashSet<>();
    @ManyToMany(mappedBy = "friends")
    private Set<User> friendshipOwners = new HashSet<>();

    public void addMessageRecipient(MessageRecipient recipientToAdd) {
        messageRecipients.add(recipientToAdd);
    }

    public void removeMessageRecipient(MessageRecipient recipientToRemove) {
        Iterator<MessageRecipient> i = messageRecipients.iterator();
        while (i.hasNext()) {
            MessageRecipient mr = (MessageRecipient) i.next();
            if (mr.equals(recipientToRemove)) {
                i.remove();
            }
        }
    }

    public void addFriend(User friend) {
        friendshipOwners.add(this);
        friends.add(friend);
    }

    public void removeFriend(User friendToRemove) {
        Iterator<User> i = friends.iterator();
        while (i.hasNext()) {
            User u = (User)i.next();
            i.remove();
        }
        if (friends.size() == 0) {
            friendshipOwners.remove(this);
        }
    }

    public void addMessage(Message message) {
        messages.add(message);
        message.setMessageCreator(this);
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setMessageCreator(null);
    }

    public void addRoom(Room room) {
        rooms.add(room);
        room.getUsers().add(this);
    }

    public void removeRoom(Room room) {
        rooms.remove(room);
        room.getUsers().add(null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getSex() == user.getSex() &&
                isLoggedIn() == user.isLoggedIn() &&
                getId().equals(user.getId()) &&
                getNick().equals(user.getNick()) &&
                getName().equals(user.getName()) &&
                getLocation().equals(user.getLocation()) &&
                getCreatedOn().equals(user.getCreatedOn()) &&
                getPassword().equals(user.getPassword());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNick(), getName(), getSex(), getLocation(), getCreatedOn(), getPassword(), isLoggedIn());
    }
}
