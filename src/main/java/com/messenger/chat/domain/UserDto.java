package com.messenger.chat.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public final class UserDto {
    @JsonProperty("id")
    private Long id;

    @JsonProperty("nick")
    private String nick;

    @JsonProperty("name")
    private String name;

    @JsonProperty("sex")
    private char sex;

    @JsonProperty("location")
    private String location;

    @JsonProperty("createdOn")
    private LocalDate createdOn;

    @JsonProperty("password")
    private String password;

    @JsonProperty("loggedIn")
    private boolean loggedIn;

}
