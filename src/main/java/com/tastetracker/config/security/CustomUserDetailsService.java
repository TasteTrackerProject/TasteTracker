package com.tastetracker.config.security;

import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserCredentialsDto;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService
{
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername( String login ) throws UsernameNotFoundException
    {
        return userService.findCredentialsByLogin( login )
            .map( this::mapToUserDetails )
            .orElseThrow( () -> new UsernameNotFoundException( String.format( "User with login %s not found", login ) ) );
    }

    private UserDetails mapToUserDetails( UserCredentialsDto userCredentials )
    {
        return User.builder()
                .username( userCredentials.getLogin() )
                .password( userCredentials.getPassword() )
                .roles( userCredentials.getRoles().toArray( String[]::new ) )
                .build();
    }
}
