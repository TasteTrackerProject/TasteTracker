package com.tastetracker.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class SecurityConfig
{
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String USER_ROLE = "USER";
    private static final String EDITOR_ROLE = "EDITOR";

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity ) throws Exception
    {
        httpSecurity.authorizeHttpRequests( ( aut ) -> aut
            .requestMatchers( "/admin/**" ).hasAnyRole( EDITOR_ROLE, ADMIN_ROLE )
            .anyRequest().permitAll()
        )
            .formLogin( login -> login
                .loginPage( "/login" )
                .permitAll() )
            .logout( logout -> logout
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout/**", HttpMethod.GET.name() ) )
                .logoutSuccessUrl( "/login?logout" ).permitAll() );

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer()
    {
        return web -> web.ignoring().requestMatchers(
            "/img/**",
            "/scripts/**",
            "/styles/**"
        );
    }
}
