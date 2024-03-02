package com.tastetracker.domain.user;

import com.tastetracker.config.security.SystemRoles;
import com.tastetracker.domain.user.dto.UserCredentialsDto;
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

    public final UserRepository userRepository;
    public final UserRoleRepository userRoleRepository;
    public final PasswordEncoder passwordEncoder;



    public Optional<UserCredentialsDto> findCredentialsByLogin( String login )
    {
        return userRepository.findByLogin( login )
            .map( UserCredentialsDtoMapper::map );
    }

    @Transactional
    public void registerUserWithDefaultRole( UserRegistrationDto userRegistration )
    {
        UserRole userRole = userRoleRepository.findByName( SystemRoles.USER.getRole() ).orElseThrow();
        User user = new User();

        user.setEmail( userRegistration.getEmail() );
        user.setLogin( userRegistration.getLogin() );
        user.setPassword( passwordEncoder.encode( userRegistration.getPassword() ) );
        user.getRoles().add( userRole );
        userRepository.save( user );
    }
}
