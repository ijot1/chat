package com.messenger.chat.repository;

import com.messenger.chat.domain.UserRoom;
import com.messenger.chat.domain.UserRoomId;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

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
