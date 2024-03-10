package com.tastetracker.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
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
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity,MvcRequestMatcher.Builder mvc ) throws Exception
    {
        httpSecurity.authorizeHttpRequests(  aut  -> aut
                .requestMatchers( "/admin/**" ).hasAnyRole( SystemRoles.EDITOR.getRole(), SystemRoles.ADMIN.getRole() )
                .requestMatchers(mvc.pattern(HttpMethod.POST, "/api/review/**")).hasAnyRole(SystemRoles.EDITOR.getRole(),SystemRoles.ADMIN.getRole(),SystemRoles.USER.getRole())
                .requestMatchers( mvc.pattern( HttpMethod.GET, "/api/review/rating/**" ) ).permitAll()
                .anyRequest().permitAll()
        )
            .formLogin( login -> login
                .loginPage( "/login" )
                .permitAll() )
            .logout( logout -> logout
                .logoutRequestMatcher( new AntPathRequestMatcher( "/logout/**", HttpMethod.GET.name() ) )
                .logoutSuccessUrl( "/login?logout" ).permitAll() );
        httpSecurity.csrf( csrf -> csrf
            .ignoringRequestMatchers( new AntPathRequestMatcher( "/h2-console/**" ) )
            .csrfTokenRepository( csrfTokenRepository()));
        httpSecurity.headers( headers -> headers
            .frameOptions( HeadersConfigurer.FrameOptionsConfig::sameOrigin )
        );

        return httpSecurity.build();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
        repository.setSessionAttributeName("_csrf"); // Domyślna nazwa atrybutu sesji przechowującego token CSRF
        return repository;
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
