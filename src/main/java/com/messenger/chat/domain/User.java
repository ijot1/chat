package com.messenger.chat.domain;

import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
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
    private LocalDate createdOn;

    @Column(name = "PASSWORD", nullable = false)
    private String password;

    @Column(name = "LOGGED_IN")
    private boolean loggedIn;

    @OneToMany(targetEntity = Message.class,
            mappedBy = "creator",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<Message> messages = new HashSet<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<MessageRecipient> messageRecipients = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)  //or CascadeType.MERGE
    private Set<User> friends = new HashSet<>();

    @ManyToMany(mappedBy = "friends")
    private Set<User> friendshipOwners = new HashSet<>();

    public User(String nick) {
        this.nick = nick;
    }

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
            User u = (User) i.next();
            i.remove();
        }
        if (friends.size() == 0) {
            friendshipOwners.remove(this);
        }
    }

    public void addMessage(Message message) {
        this.messages.add(message);
        message.setCreator(this);
    }

    public void removeMessage(Message message) {
        this.messages.remove(message);
        message.setCreator(null);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nick='" + nick + '\'' +
                ", name='" + name + '\'' +
                ", sex=" + sex +
                ", loggedIn=" + loggedIn +
                ", messageRecipients=" + messageRecipients +
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
        return Objects.hash(id, nick, name, sex, location);
    }
}
