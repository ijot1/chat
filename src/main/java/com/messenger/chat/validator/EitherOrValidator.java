package com.messenger.chat.validator;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.domain.MessageRecipient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EitherOrValidator implements ConstraintValidator<EitherOr, MessageRecipient> {
    public void initialize(EitherOr arg0) {
    }

    @Override
    public boolean isValid(MessageRecipient recipient, ConstraintValidatorContext ctx) {
        return (recipient.getMessage() != null && recipient.getUser() != null && recipient.getUserRoom() == null)
                || (recipient.getMessage() != null && recipient.getUser() == null && recipient.getUserRoom() != null);
    }
}
