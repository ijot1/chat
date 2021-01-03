package com.messenger.chat.repository;

import com.messenger.chat.domain.MessageRecipient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MessageRecipientRepository extends CrudRepository<MessageRecipient, Long> {

    @Override
    List<MessageRecipient> findAll();

    @Override
    Optional<MessageRecipient> findById(Long id);

    @Override
    MessageRecipient save(MessageRecipient messageRecipient);

    @Override
    void deleteById(Long id);
}
