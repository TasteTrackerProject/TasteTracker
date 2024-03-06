package com.tastetracker.domain.user;

import com.tastetracker.config.security.SystemRoles;
import com.tastetracker.config.token.VeryficationToken;
import com.tastetracker.config.token.VeryficationTokenRepository;
import com.tastetracker.domain.user.dto.UserCredentialsDto;
import com.tastetracker.domain.user.dto.UserDto;
import com.tastetracker.domain.user.dto.UserRegistrationDto;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService
{

    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VeryficationTokenRepository veryficationTokenRepository;



    public Optional<UserCredentialsDto> findCredentialsByLogin( String login )
    {
        return userRepository.findByLogin( login )
            .map( UserCredentialsDtoMapper::map );
    }

    @Transactional
    public User registerUserWithDefaultRole( UserRegistrationDto userRegistration )
    {
        UserRole userRole = userRoleRepository.findByName( SystemRoles.USER.getRole() ).orElseThrow();
        User user = new User();

        user.setEmail( userRegistration.getEmail() );
        user.setLogin( userRegistration.getLogin() );
        user.setPassword( passwordEncoder.encode( userRegistration.getPassword() ) );
        user.getRoles().add( userRole );
        userRepository.save( user );
        return user;
    }

    public Optional<UserDto> findUserByLogin( String login )
    {
        return userRepository.findByLogin( login )
            .map( UserDtoMapper::map );
    }

    public void saveVeryficationToken( User user, String token )
    {
        VeryficationToken tokenToSave = new VeryficationToken();
        tokenToSave.setToken( token );
        tokenToSave.setUser( user );

        veryficationTokenRepository.save( tokenToSave );
    }

    public Optional<VeryficationToken> getVeryficationToken( String veryficationToken )
    {
        return veryficationTokenRepository.findByToken( veryficationToken );
    }

    @Transactional
    public void setUserEnabled( User user )
    {
        user.setEnabled( true );
    }
}
