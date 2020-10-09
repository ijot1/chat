package com.messenger.chat.mapper;

import com.messenger.chat.domain.MessageRecipient;
import com.messenger.chat.domain.MessageRecipientDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class MessageRecipientMapper {
    public MessageRecipient mapToMessageRecipient(MessageRecipientDto messageRecipientDto) {
        return MessageRecipient.builder()
                .id(messageRecipientDto.getId())
                .build();
    }

    public MessageRecipientDto mapToMessageRecipientDto(MessageRecipient messageRecipient) {
        return MessageRecipientDto.builder()
                .id(messageRecipient.getId())
                .build();
    }

    public List<MessageRecipientDto> mapToMessageRecipientDtoList(List<MessageRecipient> messageRecipientList) {
        return messageRecipientList.stream()
                .map(this::mapToMessageRecipientDto)
                .collect(Collectors.toList());
    }
}
