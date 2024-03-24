package com.tastetracker.web;

import com.tastetracker.config.token.VeryficationToken;
import com.tastetracker.domain.user.User;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserRegistrationDto;
import com.tastetracker.event.OnRegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Optional;

@Controller
@AllArgsConstructor
public class RegistrationController
{
    private final UserService userService;
    private final ApplicationEventPublisher eventPublisher;
    @GetMapping("/registration")
    public String loginForm( Model model )
    {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute( "user", userRegistrationDto );
        return "registration-form";
    }

    @PostMapping("/registration")
    public String register( UserRegistrationDto userRegistrationDto, HttpServletRequest request )
    {
        eventPublisher.publishEvent( new OnRegistrationCompleteEvent( userRegistrationDto ) );

        return "redirect:/registration-success";
    }

    @GetMapping("/registration-success")
    public String registrationSuccess()
    {
        return "registration-success";
    }

    @GetMapping("/account-activation")
    public String confirmRegistration( @RequestParam("token") String token )
    {
        Optional<VeryficationToken> tokenFromRepository = userService.getVeryficationToken( token );

        if( !tokenFromRepository.isPresent() )
        {
            return "redirect:404";
        }

        VeryficationToken veryficationToken = tokenFromRepository.get();
        User user = veryficationToken.getUser();

        userService.setUserEnabled( user );

        return "redirect:/account-activation-success";
    }

    @GetMapping("/account-activation-success")
    public String accountActivationSuccess( )
    {
        return "account-activation-success";
    }

    @ExceptionHandler( { MessagingException.class, IOException.class } )
    public String handleException()
    {
        return "user-registration-fail";
    }

}
