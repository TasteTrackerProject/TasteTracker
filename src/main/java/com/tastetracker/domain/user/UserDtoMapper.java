package com.tastetracker.domain.user;

import com.tastetracker.domain.user.dto.UserDto;

public class UserDtoMapper
{
    static UserDto map( User user )
    {
        return new UserDto(
            user.getId(),
            user.getLogin()
        );
    }
}
