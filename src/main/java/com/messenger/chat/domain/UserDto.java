package com.messenger.chat.domain;

import lombok.*;

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
    private String password;
    private boolean loggedIn;
}
