package com.messenger.chat.controller;

import com.messenger.chat.domain.MessageRecipientDto;
import com.messenger.chat.service.MessageRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("v1/chat")
public class MessageRecipientController {
    @Autowired
    MessageRecipientService messageRecipientService;

    @GetMapping(value = "getRecipients")
    List<MessageRecipientDto> getRecipients() {
        return messageRecipientService.getMessageRecipients();
    }
}
