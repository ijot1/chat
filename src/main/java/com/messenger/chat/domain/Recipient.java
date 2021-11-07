package com.messenger.chat.domain;

import com.messenger.chat.annotation.EitherOr;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
//@EitherOr
@Table(name = "RECIPIENTS")
@NamedNativeQuery(
        name = "findRecipientsByMessageIdAndUserIdOrUserRoomId_RoomId",
        query = "SELECT r.* FROM recipients r where r.message_id = :messageId " +
                "and ((r.user_id = :userId " +
                "and (r.id_room is null or r.id_user is null)) " +
                "or (r.user_id is null and r.id_room = :roomId))",

        resultClass = Recipient.class
)

public class Recipient implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "ADDED_ON", updatable = false)
    @NotNull
    private LocalDate addedOn;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
            @JoinColumn(name = "id_user", referencedColumnName = "USER_ID"),
            @JoinColumn(name = "id_room", referencedColumnName = "ROOM_ID")
    })
    private UserRoom userRoom;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY/*,
    cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}*/
    )
    /*  *** Removing child association cascading ***
        # You need to remove the @CascadeType.ALL from the @ManyToOne association.
        # Child entities should not cascade to parent associations.
        # Only parent entities should cascade to child entities.*/
    @JoinColumn(name = "USER_ID")
    private User user;

    public void setUser(User user) {
        setUser(user, true);
    }

    void setUser(User user, boolean add) {
        this.user = user;
        if (user != null && add) {
            user.addRecipient(this, false);
        }
    }

    @Override
    public String toString() {
        return "Recipient{" +
                "id=" + id +
                ", addedOn=" + addedOn +
                ", message=" + message +
                ", userRoom=" + userRoom.getRoom().getName() +
                ", user=" + user.getName() +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Recipient)) return false;
        Recipient that = (Recipient) o;
        return getId().equals(that.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}