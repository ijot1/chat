package com.messenger.chat.annotation;

import com.messenger.chat.validator.EitherOrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherOrValidator.class})
public @interface EitherOr {
    String message() default "Recipient can only be User or UserRoom.";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
