package com.messenger.chat.repository;

import com.messenger.chat.domain.UserRoom;
import com.messenger.chat.domain.UserRoomId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRoomRepository extends CrudRepository<UserRoom, UserRoomId> {

    @Override
    List<UserRoom> findAll();

    @Override
    Optional<UserRoom> findById(UserRoomId id);

    @Override
    UserRoom save(UserRoom userRoom);

    @Override
    void delete(UserRoom userRoom);
}
