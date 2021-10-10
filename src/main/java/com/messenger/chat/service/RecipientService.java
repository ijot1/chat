package com.messenger.chat.service;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.domain.*;
import com.messenger.chat.exception.EntityNotFoundException;
import com.messenger.chat.repository.RecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@EitherOr
@EitherOrId
public class RecipientService {

    @Autowired
    private RecipientRepository recipientRepository;

    public List<Recipient> retrieveRecipients() {
        return recipientRepository.findAll();
    }

    public List<Recipient> retrieveRecipientByParametersId(Long messageId, UserRoomId userRoomId, Long userId) {
        /*return recipientRepository.findRecipientByMessageIdAndUserIdOrUserRoomId(messageId, userRoomId.getRoomId(), userId);*/
        return recipientRepository.findRecipientsByMessageIdAndUserIdOrUserRoomId_RoomId(messageId, userRoomId.getRoomId(), userId);
    }

    public Recipient retrieveRecipientById(Long id) throws EntityNotFoundException {
        return recipientRepository.findById(id).orElseThrow(() -> new EntityNotFoundException(Recipient.class, id));
    }

    public Recipient saveRecipient(Recipient recipient) {
        return recipientRepository.save(recipient);
    }

    public void deleteRecipientById(Long id) {
        recipientRepository.deleteById(id);
    }
}
