package com.tastetracker.domain.user;

import com.tastetracker.domain.user.dto.UserCredentialsDto;

import java.util.Set;
import java.util.stream.Collectors;

public class UserCredentialsDtoMapper
{
    static UserCredentialsDto map( User user )
    {
        String login = user.getLogin();
        String email = user.getEmail();
        String password = user.getPassword();
        Set<String> roles = user.getRoles()
            .stream()
            .map( UserRole::getName )
            .collect( Collectors.toSet() );
        return new UserCredentialsDto( login, email, password, roles );
    }
}
