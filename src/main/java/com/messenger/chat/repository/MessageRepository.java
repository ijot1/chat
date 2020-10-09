package com.messenger.chat.repository;

import com.messenger.chat.domain.Message;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface MessageRepository extends CrudRepository<Message, Long> {
    @Override
    List<Message> findAll();

    @Override
    Optional<Message> findById(Long id);

    @Override
    Message save(Message message);

    @Override
    void deleteById(Long id);
}
