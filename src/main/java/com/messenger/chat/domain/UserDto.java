package com.messenger.chat.domain;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserDto {
    private Long id;
    private String nick;
    private String name;
    private char sex;
    private String location;
    private LocalDate createdOn;
    private String password;
    private boolean loggedIn;
}
