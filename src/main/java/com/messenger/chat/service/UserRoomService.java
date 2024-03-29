package com.messenger.chat.service;

import com.messenger.chat.domain.*;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.exception.ResourceNotFoundException;
import com.messenger.chat.mapper.UserRoomMapper;
import com.messenger.chat.repository.RecipientRepository;
import com.messenger.chat.repository.RoomRepository;
import com.messenger.chat.repository.UserRepository;
import com.messenger.chat.repository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class UserRoomService {

    @Autowired
    private UserRoomMapper userRoomMapper;

    @Autowired
    private UserRoomRepository userRoomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RecipientRepository recipientRepository;

    @Autowired
    private EntityManager em;

    @PersistenceUnit
    public EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("ChatPU");


    public List<UserRoom> retrieveUsersRooms() {
        return userRoomRepository.findAll();
    }

    public UserRoom retrieveUserRoom(final Long userId, final Long roomId)
            throws ResourceNotFoundException {
        Optional<UserRoom> foundUserRoom = userRoomRepository.findAll().stream()
                .filter(el -> (el.getId().getUserId() == userId
                        && el.getId().getRoomId() == roomId)).findFirst();
        return foundUserRoom.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
    }

    public List<UserRoom> retrieveUsersRoomsByRoomId(final Long roomId) {
        return userRoomRepository.findByRoomId(roomId);
    }

    public UserRoom create(UserRoomDto userRoomDto) {
        return userRoomRepository.save(serviceMap(userRoomDto));
    }

    public UserRoom saveUserRoom(UserRoom userRoom) {
        return userRoomRepository.save(userRoom);
    }

    public void removeUserRoom(final Long userId, final Long roomId) {

        em = emFactory.createEntityManager();
        em.getTransaction().begin();

        User user = em.find(User.class, userId);
        Room room = em.find(Room.class, roomId);

        em.flush();
        em.getTransaction().commit();
        em.close();

        userRoomRepository.deleteByUserAndRoom(user, room);
    }

    private UserRoom serviceMap(UserRoomDto userRoomDto) {
        User user = userRepository.findById(userRoomDto.getUserId()).orElseThrow(()
                -> new EntityNotFoundException(User.class, userRoomDto.getUserId()));
        Room room = roomRepository.findById(userRoomDto.getRoomId()).orElseThrow(()
                -> new EntityNotFoundException(Room.class, userRoomDto.getRoomId()));
        System.out.println("userRoom = " + userRoomMapper.mapToUserRoom(userRoomDto, user, room).toString());
        return userRoomMapper.mapToUserRoom(userRoomDto, user, room);
    }

}
