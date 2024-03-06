package com.tastetracker.web.listener;

import com.tastetracker.domain.email.RegistrationEmailServiceImpl;
import com.tastetracker.domain.user.User;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.event.OnRegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{

    private final RegistrationEmailServiceImpl registrationEmailService;
    private final UserService userService;
    @Value( "${spring.accountactivation.template}" )
    private String templateToConfirmationEmailPath;

    @SneakyThrows
    @Override
    @Transactional
    public void onApplicationEvent( OnRegistrationCompleteEvent event )
    {
        this.confirmRegistration( event );
    }

    public void confirmRegistration( OnRegistrationCompleteEvent event ) throws MessagingException, IOException
    {
        User registerUserWithDefaultRole = userService.registerUserWithDefaultRole( event.getUserRegistrationDto() );

        String token = UUID.randomUUID().toString();

        userService.saveVeryficationToken( registerUserWithDefaultRole, token );

        String recipientAddress = registerUserWithDefaultRole.getEmail();
        String subject = "Potwierdzenie rejestracji konta w serwisie TasteTracker";

        String appBaseUrl = getAppBaseUrl();
        String confirmationUrl = appBaseUrl + "/account-activation?token=" + token;

        registrationEmailService.sendDynamicEmail( recipientAddress, subject, templateToConfirmationEmailPath, confirmationUrl );
    }

    private static String getAppBaseUrl()
    {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        String appBaseUrl = request.getRequestURL().toString().replace( request.getRequestURI(), request.getContextPath() );

        return appBaseUrl;
    }
}
