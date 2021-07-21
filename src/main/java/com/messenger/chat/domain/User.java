package com.messenger.chat.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@NamedNativeQueries({
        @NamedNativeQuery(
                name = "User.findFriends",
                query = "select f.* from users_friends uf, users u, users f " +
                        "where uf.user_id = u.user_id and uf.friend_id = f.user_id " +
                        "and u.user_id = :id",  //and u.user_id = :id
                resultClass = User.class),
        @NamedNativeQuery(
                name = "User.addUsersFriend",
                query = "insert into users_friends (user_id, friend_id) values (:userId, :friendId)",   //values (:userId, :friendId)
                resultClass = User.class),
        @NamedNativeQuery(
                name = "User.deleteUsersFriend",
                query = "delete from users_friends where friend_id = :friendId and user_id = :userId",  //where friend_id = :friendId and user_id = :userId
                resultClass = User.class)
})

//@JsonIgnoreProperties(value = {"messages", "recipients", "friends", "senders"})
@Table(name = "USERS")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
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
    private LocalDate createdOn = LocalDate.now();

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LOGGED_IN")
    private boolean loggedIn;

    @OneToMany(mappedBy = "creator",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private Set<Message> messages;

    @OneToMany(mappedBy = "user",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            orphanRemoval = true)
    @JsonIgnore
    private Set<Recipient> recipients;

    @OneToMany(mappedBy = "user",
            cascade = {CascadeType.ALL},
            orphanRemoval = true)
    private Set<UserRoom> userUsersRooms = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    @JoinTable(name = "users_friends",
            joinColumns = {@JoinColumn(name = "USER_ID")},
            inverseJoinColumns = {@JoinColumn(name = "FRIEND_ID")})
    private Set<User> friends = new HashSet<>();

    public User(String nick) {
        this.nick = nick;
    }

    public void addMessage(Message message) {
        addMessage(message, true);
    }

    void addMessage(Message message, boolean set) {
        if (message != null && !getMessages().contains(message)) {
            getMessages().add(message);
        }
        if (set) {
            message.setCreator(this, false);
        }
    }

    public void removeMessage(Message message) {
        messages.remove(message);
        message.setCreator(null);
    }

    public void addRecipient(Recipient recipient) {
        addRecipient(recipient, true);
    }

    void addRecipient(Recipient recipientToAdd, boolean set) {
        if (recipientToAdd != null && !getRecipients().contains(recipientToAdd)) {
            getRecipients().add(recipientToAdd);
            if (set) {
                recipientToAdd.setUser(this, false);
            }
        }
    }

    public void removeRecipient(Recipient recipientToRemove) {
        Iterator<Recipient> i = recipients.iterator();
        while (i.hasNext()) {
            Recipient mr = i.next();
            if (mr.equals(recipientToRemove)) {
                i.remove();
                recipientToRemove.setUser(null);
            }
        }
    }

    public void addUserRoomToUser(Room room) {
        UserRoom userRoom = new UserRoom(this, room);
        if (!userUsersRooms.contains(userRoom)) {
            userUsersRooms.add(userRoom);
            room.getRoomUsersRooms().add(userRoom);
        }
    }

    public void removeUserRoomFromUser(Room room) {
        Iterator<UserRoom> iterator = userUsersRooms.iterator();
        while (iterator.hasNext()) {
            UserRoom ur = iterator.next();
            if (ur.getUser().equals(this) && ur.getRoom().equals(room)) {
                iterator.remove();
                ur.getRoom().getRoomUsersRooms().remove(ur);
                ur.setUser(null);
                ur.setRoom(null);
            }
        }
    }

    public void addFriend(User friend) {
        this.friends.add(friend);
    }

    public void removeFriend(User friendToRemove) {
        Iterator<User> i = friends.iterator();
        while (i.hasNext()) {
            User u = (User) i.next();
            if (u == friendToRemove) {
                i.remove();
            }
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", loggedIn=" + loggedIn +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        final User user = (User) o;
        if (id != null && user.getId() != null) {
            return id.equals(user.getId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nick, name, sex, location);
    }
}
