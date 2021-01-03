package com.messenger.chat.annotation;

import com.messenger.chat.validator.EitherOrValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EitherOrValidator.class})
public @interface EitherOr {
    String message() default "A recipient can only be User or UserRoom.";
    Class<?> [] groups() default {};
    Class<? extends Payload> [] payload() default {};
}
