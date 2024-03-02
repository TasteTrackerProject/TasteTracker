package com.tastetracker.domain.user;

import com.tastetracker.domain.user.dto.UserCredentialsDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService
{
    public final UserRepository userRepository;

    public Optional<UserCredentialsDto> findCredentialsByLogin( String login )
    {
        return userRepository.findByLogin( login )
            .map( UserCredentialsDtoMapper::map );
    }
}
