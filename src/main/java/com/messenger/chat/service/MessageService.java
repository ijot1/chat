package com.messenger.chat.service;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.MessageDto;
import com.messenger.chat.mapper.MessageMapper;
import com.messenger.chat.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MessageService {
    private MessageRepository messageRepository;
    private MessageMapper messageMapper;

    @Autowired
    public MessageService(MessageRepository messageRepository, MessageMapper messageMapper) {
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }

    public List<MessageDto> getMessages() {
        return messageMapper.mapToMessageDtoList(messageRepository.findAll());
    }

    public MessageDto getMessageById(Long id) {
        return messageMapper.mapToMessageDto(messageRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    public void deleteMessage(Long id) {
        messageRepository.deleteById(id);
    }

    public Message saveMessage(Message message) { return messageRepository.save(message); }
}
