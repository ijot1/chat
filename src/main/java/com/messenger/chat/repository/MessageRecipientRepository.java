package com.messenger.chat.repository;

import com.messenger.chat.domain.MessageRecipient;
import com.messenger.chat.domain.MessageRecipientId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface MessageRecipientRepository extends CrudRepository<MessageRecipient, MessageRecipientId> {

    @Override
    List<MessageRecipient> findAll();

    @Override
    MessageRecipient save(MessageRecipient messageRecipient);

    @Override
    void deleteById(MessageRecipientId id);
}
