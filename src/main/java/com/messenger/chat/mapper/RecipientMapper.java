package com.messenger.chat.mapper;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@EitherOr
@EitherOrId
public class RecipientMapper {
    public Recipient mapToRecipient
            (final RecipientDto recipientDto, final Message message, final User user, final UserRoom userRoom) {
        Recipient recipient = new Recipient();
        if (message != null && user != null && userRoom == null) {
            recipient = new Recipient(
                    recipientDto.getId(),
                    recipientDto.getAddedOn(),
                    message,
                    null,
                    user
            );
        } else if (message != null && user == null && userRoom != null) {
            recipient = new Recipient(
                    recipientDto.getId(),
                    recipientDto.getAddedOn(),
                    message,
                    userRoom,
                    null
            );
        }
        return recipient;
    }

    public RecipientDto mapToRecipientDto(Recipient recipient) {
        RecipientDto recipientDto = new RecipientDto();
        if (recipient.getMessage() != null && recipient.getUser() != null && recipient.getUserRoom() == null) {
            recipientDto = new RecipientDto(
                    recipient.getId(),
                    recipient.getAddedOn(),
                    recipient.getMessage().getId(),
                    null,
                    null,
                    recipient.getUser().getId()
            );
        } else if (recipient.getMessage() != null && recipient.getUser() == null && recipient.getUserRoom() != null) {
            recipientDto = new RecipientDto(
                    recipient.getId(),
                    recipient.getAddedOn(),
                    recipient.getMessage().getId(),
                    recipient.getUserRoom().getId().getUserId(),
                    recipient.getUserRoom().getId().getRoomId(),

                    null
            );
        }
        return recipientDto;
    }

    public List<RecipientDto> mapToRecipientDtoList(List<Recipient> recipientList) {
        return recipientList.stream()
                .map(this::mapToRecipientDto)
                .collect(Collectors.toList());
    }
}