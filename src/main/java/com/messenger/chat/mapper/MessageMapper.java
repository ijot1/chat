package com.messenger.chat.mapper;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.MessageDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageMapper {
    public Message mapToMessage(MessageDto messageDto) {
        return Message.builder()
                .id(messageDto.getId())
                .messageText(messageDto.getMessageText())
                .dateCreated(messageDto.getDateCreated())
                .build();
    }

    public MessageDto mapToMessageDto(Message message) {
        return MessageDto.builder()
                .id(message.getId())
                .messageText(message.getMessageText())
                .dateCreated(message.getDateCreated())
                .build();
    }

    public List<MessageDto> mapToMessageDtoList(List<Message> messageList) {
        return messageList.stream()
                .map(this::mapToMessageDto)
                .collect(Collectors.toList());
    }
}
