package com.messenger.chat.controller;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.domain.*;
import com.messenger.chat.mapper.RecipientMapper;
import com.messenger.chat.service.RecipientService;
import com.messenger.chat.service.MessageService;
import com.messenger.chat.service.UserRoomService;
import com.messenger.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@CrossOrigin("*")
@RestController
//@EitherOr
//@EitherOrId
@RequestMapping(value = "/v1/chat")
public class RecipientController {
    @Autowired
    RecipientService recipientService;

    @Autowired
    RecipientMapper recipientMapper;

    @Autowired
    MessageService messageService;

    @Autowired
    UserRoomService userRoomService;

    @Autowired
    UserService userService;


    @GetMapping(value = "/recipients")
    public List<RecipientDto> getRecipients() {
        return recipientMapper.mapToRecipientDtoList(recipientService.retrieveRecipients());
    }

    @GetMapping(value = "/recipients/{recipientId}")
    public RecipientDto getRecipient(@PathVariable Long recipientId) {
        return recipientMapper.mapToRecipientDto(recipientService.retrieveRecipientById(recipientId));
    }

    @PostMapping(value = "/recipients", consumes = APPLICATION_JSON_VALUE)
    public void createRecipient(@RequestBody RecipientDto recipientDto) {
        Message message = messageService.retrieveMessageById(recipientDto.getMessageId());
        if (recipientDto.getUserRoomUserId() == null && recipientDto.getUserRoomRoomId() == null && recipientDto.getUserId() != null) {
//            UserRoom userRoom = userRoomService.retrieveUserRoomById(recipientDto.getUserRoomId());
            User user = userService.retrieveUserById(recipientDto.getUserId());
            recipientService.saveRecipient(recipientMapper.mapToRecipient(
                    recipientDto, message, user, null));
        } else if (recipientDto.getUserRoomUserId() != null && recipientDto.getUserRoomRoomId() != null && recipientDto.getUserId() == null) {
            UserRoom userRoom = userRoomService.retrieveUserRoomById(new UserRoomId(recipientDto.getUserRoomUserId(), recipientDto.getUserRoomRoomId()));
//            User user = userService.retrieveUserById(recipientDto.getUserId());
            recipientService.saveRecipient(recipientMapper.mapToRecipient(
                    recipientDto, message, null, userRoom));
        }
    }

    @DeleteMapping(value = "/recipients/{recipientId}")
    public void deleteRecipient(@PathVariable Long recipientId) {
        recipientService.deleteRecipientById(recipientId);
    }

}
