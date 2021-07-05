package com.messenger.chat.domain;

import com.messenger.chat.annotation.EitherOr;
import lombok.*;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@EitherOr
@Table(name = "RECIPIENTS")
@NamedNativeQuery(
        name = "findRecipientByMessageIdAndUserIdOrUserRoomId",
        query = "SELECT r.* FROM recipients r where r.message_id = :message_id and ((r.user_id = :user_id and r.id_room is null and r.id_user is null) or (r.id_room = :room_id and r.user_id is null))",
        resultClass = Recipient.class
)

public class Recipient {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECIPIENT_ID")
    private Long id;

    @Column(name = "ADDED_ON", updatable = false)
    @NotNull
    private LocalDate addedOn;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)   //FetchType.LAZY
    @JoinColumn(name = "MESSAGE_ID")
    private Message message;

    @Nullable
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)   //FetchType.LAZY
    @JoinColumns({
            @JoinColumn(name = "id_user", referencedColumnName = "USER_ID"),
            @JoinColumn(name = "id_room", referencedColumnName = "ROOM_ID")
    })
    private UserRoom userRoom;

    @Nullable
    @ManyToOne(cascade = CascadeType.MERGE)
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
                ", userRoom=" + userRoom +
                ", user=" + user +
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
        return Objects.hashCode(id);
    }
}