package com.tastetracker.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;


@Configuration
public class SecurityConfig
{
    @Bean
    MvcRequestMatcher.Builder mvc( HandlerMappingIntrospector introspector) {
        return new MvcRequestMatcher.Builder(introspector);
    }
    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity , MvcRequestMatcher.Builder mvc) throws Exception
    {
        httpSecurity.authorizeHttpRequests( ( aut ) -> aut
            .requestMatchers( "/admin/**" ).hasAnyRole( SystemRoles.EDITOR.getRole(), SystemRoles.ADMIN.getRole() )
                .requestMatchers(mvc.pattern(HttpMethod.POST, "/api")).hasRole("ADMIN")
            .anyRequest().permitAll()
        )
            .formLogin( login -> login
                .loginPage( "/login" )
                .permitAll() )
            .logout( logout -> logout
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout/**", HttpMethod.GET.name() ) )
                .logoutSuccessUrl( "/login?logout" ).permitAll() );
        httpSecurity.csrf().ignoringRequestMatchers( new AntPathRequestMatcher( "/h2-console/**" ) );
        httpSecurity.headers().frameOptions().sameOrigin();

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

    @Bean
    public PasswordEncoder passwordEncoder()
    {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
