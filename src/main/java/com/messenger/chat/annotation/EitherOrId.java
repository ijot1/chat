package com.messenger.chat.annotation;

import com.messenger.chat.validator.EitherOrIdValidator;
import com.messenger.chat.validator.EitherOrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherOrIdValidator.class})
public @interface EitherOrId {
    String message() default "MessageRecipientDto can have either UserId or UserRoomId.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

