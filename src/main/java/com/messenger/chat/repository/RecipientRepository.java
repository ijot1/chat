package com.messenger.chat.repository;

import com.messenger.chat.domain.Recipient;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RecipientRepository extends CrudRepository<Recipient, Long> {

    @Query(nativeQuery = true)
    List<Recipient> findRecipientByMessageIdAndUserIdOrUserRoomId(
            @Param("messageId") Long messageId,
            @Nullable
            @Param("roomId") Long roomId,
            @Nullable
            @Param("userId") Long userId
    );

//    List<Recipient> findRecipientsByMessage(Message message);

    @Override
    List<Recipient> findAll();

    @Override
    Optional<Recipient> findById(Long id);

    @Override
    Recipient save(Recipient recipient);

    @Modifying
    @Query("delete from Recipient where id = :id")
    void deleteById(@Param("id") Long id);
}
