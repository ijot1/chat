package com.messenger.chat.repository;

import com.messenger.chat.domain.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    @Override
    List<Room> findAll();

    @Override
    Optional<Room> findById(Long id);

    @Override
    Room save(Room room);

    @Override
    void deleteById(Long id);
}
