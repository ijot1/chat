package com.messenger.chat.mapper;

import com.messenger.chat.domain.Message;
import com.messenger.chat.domain.MessageDto;
import com.messenger.chat.domain.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static jdk.nashorn.internal.runtime.Debug.id;

@Component
public class MessageMapper {
    public Message mapToMessage(final MessageDto messageDto, User creator) {
        return Message.builder()
                .id(messageDto.getId())
                .messageText(messageDto.getMessageText())
                .dateCreated(messageDto.getDateCreated())
                .creator(creator)
                .build();
    }

    public MessageDto mapToMessageDto(Message message) {
        return new MessageDto(
                message.getId(),
                message.getMessageText(),
                message.getDateCreated(),
                message.getCreator().getId()
        );
    }

    public List<MessageDto> mapToMessageDtoList(List<Message> messageList) {
        return messageList.stream()
                .map(this::mapToMessageDto)
                .collect(Collectors.toList());
    }
}
