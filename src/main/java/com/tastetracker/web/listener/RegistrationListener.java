package com.tastetracker.web.listener;

import com.tastetracker.domain.email.EmailService;
import com.tastetracker.domain.user.User;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.event.OnRegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.UUID;

@Component
@AllArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{

    private final EmailService emailService;
    private final UserService userService;

    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event )
    {

        this.confirmRegistration( event );

    }

    private void confirmRegistration( OnRegistrationCompleteEvent event )
    {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.createVeryficationToken( user, token );

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";

        String appBaseUrl = getAppBaseUrl();

        String confirmationUrl = appBaseUrl + "/account-activation?token=" + token;

        String message = "Cześć, bardzo nam miło, że chcesz dołączyć do naszej społeczności. Potwierdź aktywację konta poprzez wejście w link: "
            + confirmationUrl;

        emailService.sendEmail( recipientAddress, subject, message );
    }

    private static String getAppBaseUrl()
    {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        String appBaseUrl = request.getRequestURL().toString().replace( request.getRequestURI(), request.getContextPath() );

        return appBaseUrl;
    }
}
