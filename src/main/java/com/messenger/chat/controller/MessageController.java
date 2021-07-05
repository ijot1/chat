package com.messenger.chat.controller;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.MessageDto;
import com.messenger.chat.domain.User;
import com.messenger.chat.mapper.MessageMapper;
import com.messenger.chat.service.MessageService;
import com.messenger.chat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/chat")
public class MessageController {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    MessageMapper messageMapper;

    @GetMapping(value = "/messages")
    public List<MessageDto> getMessages() {
        return messageMapper.mapToMessageDtoList(messageService.retrieveMessages());
    }

    @GetMapping(value = "/messages/{messageId}")
    public MessageDto getMessage(@PathVariable Long messageId) {
        return messageMapper.mapToMessageDto(messageService.retrieveMessageById(messageId));
    }

    @PostMapping(value = "/messages")
    public MessageDto createMessage(@RequestBody MessageDto messageDto) {
        User creator = userService.retrieveUserById(messageDto.getCreatorId());
        return messageMapper.mapToMessageDto(messageService.saveMessage(messageMapper.mapToMessage(messageDto, creator)));
    }

    //updateMessage()

    @DeleteMapping(value = "/messages/{messageId}")
    public void deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessageById(messageId);
    }
}
