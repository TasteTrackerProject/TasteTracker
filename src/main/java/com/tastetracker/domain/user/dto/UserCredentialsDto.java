package com.tastetracker.domain.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;
@Data
@AllArgsConstructor
public class UserCredentialsDto
{
    private String login;
    private String email;
    private String password;
    private Set<String> roles;

}
