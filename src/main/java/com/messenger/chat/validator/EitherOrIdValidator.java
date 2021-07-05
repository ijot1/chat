package com.messenger.chat.validator;

import com.messenger.chat.annotation.EitherOrId;
import com.messenger.chat.domain.RecipientDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EitherOrIdValidator implements ConstraintValidator<EitherOrId, RecipientDto> {
    @Override
    public void initialize(EitherOrId constraintAnnotation) {}

    @Override
    public boolean isValid(RecipientDto recipientDto, ConstraintValidatorContext context) {
        return (recipientDto.getMessageId() != null && recipientDto.getUserRoomUserId() != null && recipientDto.getUserRoomRoomId() != null && recipientDto.getUserId() == null
        || recipientDto.getMessageId() != null && recipientDto.getUserRoomUserId() == null && recipientDto.getUserRoomRoomId() == null && recipientDto.getUserId() != null);
    }
}
