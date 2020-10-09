package com.messenger.chat.service;

import com.messenger.chat.domain.MessageRecipientDto;
import com.messenger.chat.mapper.MessageRecipientMapper;
import com.messenger.chat.repository.MessageRecipientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageRecipientService {
    private MessageRecipientRepository messageRecipientRepository;
    private MessageRecipientMapper messageRecipientMapper;

    @Autowired
    public MessageRecipientService(MessageRecipientRepository messageRecipientRepository,
                                   MessageRecipientMapper messageRecipientMapper) {
        this.messageRecipientRepository = messageRecipientRepository;
        this.messageRecipientMapper = messageRecipientMapper;
    }

    public List<MessageRecipientDto> getMessageRecipients() {
        return messageRecipientMapper.mapToMessageRecipientDtoList(messageRecipientRepository.findAll());
    }
}
