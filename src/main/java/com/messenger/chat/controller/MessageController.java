package com.messenger.chat.controller;

import com.messenger.chat.domain.MessageDto;
import com.messenger.chat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/chat")
public class MessageController {
    @Autowired
    MessageService messageService;

    @GetMapping(value = "getMessages")
    public List<MessageDto> getMessages() {
        return messageService.getMessages();
    }

    @GetMapping(value = "getMessage")
    public MessageDto getMessage(@RequestParam Long messageId) {
        return messageService.getMessageById(messageId);
    }

    @DeleteMapping(value = "deleteMessage")
    public void deletsMessage(@RequestParam Long messageId) {
        messageService.deleteMessage(messageId);
    }
}
