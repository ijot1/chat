package com.messenger.chat.validator;

import com.messenger.chat.annotation.EitherOr;
import com.messenger.chat.domain.Recipient;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EitherOrValidator implements ConstraintValidator<EitherOr, Recipient> {
    @Override
    public void initialize(EitherOr arg0) { }
//    public void initialize(EitherOr constraintAnnotation) { }

    @Override
    public boolean isValid(Recipient recipient, ConstraintValidatorContext ctx) {
        return (recipient.getMessage() != null && recipient.getUser() != null && recipient.getUserRoom() == null)
                || (recipient.getMessage() != null && recipient.getUser() == null && recipient.getUserRoom() != null);
    }

}
