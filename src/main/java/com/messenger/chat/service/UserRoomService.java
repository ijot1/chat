package com.messenger.chat.service;

import com.messenger.chat.domain.ResourceNotFoundException;
import com.messenger.chat.domain.UserRoom;
import com.messenger.chat.domain.UserRoomId;
import com.messenger.chat.repository.UserRoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoomService {
    @Autowired
    private UserRoomRepository userRoomRepository;

    public List<UserRoom> retrieveUsersRooms() {
        return userRoomRepository.findAll();
    }


    public UserRoom retrieveUserRoomById(final UserRoomId id) throws ResourceNotFoundException {
//        return userRoomRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("UserRoom not found for this id : " + id.toString()));
        return userRoomRepository.findById(id).orElse(null);
    }

    public UserRoom saveUserRoom(UserRoom userRoom) {
        return userRoomRepository.save(userRoom);
    }

    public void deleteUserRoomByBody(final UserRoom userRoom) {
        userRoomRepository.delete(userRoom);
    }
}
