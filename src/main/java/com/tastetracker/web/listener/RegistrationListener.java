package com.tastetracker.web.listener;

import com.tastetracker.domain.email.EmailServiceImpl;
import com.tastetracker.domain.user.User;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.event.OnRegistrationCompleteEvent;
import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.io.IOException;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent>
{
    private final EmailServiceImpl emailService;
    private final UserService userService;
    @Value( "${spring.accountactivation.template}" )
    private String templateToConfirmationEmailPath;

    public RegistrationListener( EmailServiceImpl emailService, UserService userService )
    {
        this.emailService = emailService;
        this.userService = userService;
    }


    @SneakyThrows
    @Override
    public void onApplicationEvent( OnRegistrationCompleteEvent event )
    {
        this.confirmRegistration( event );
    }


    private void confirmRegistration( OnRegistrationCompleteEvent event ) throws MessagingException, IOException
    {
        User user = event.getUser();
        String token = UUID.randomUUID().toString();

        userService.saveVeryficationToken( user, token );

        String recipientAddress = user.getEmail();
        String subject = "Potwierdzenie rejestracji";

        String appBaseUrl = getAppBaseUrl();
        String confirmationUrl = appBaseUrl + "/account-activation?token=" + token;

        emailService.sendDynamicEmail( recipientAddress, subject, templateToConfirmationEmailPath, confirmationUrl );
    }

    private static String getAppBaseUrl()
    {
        HttpServletRequest request = ( ( ServletRequestAttributes ) RequestContextHolder.currentRequestAttributes() ).getRequest();
        String appBaseUrl = request.getRequestURL().toString().replace( request.getRequestURI(), request.getContextPath() );

        return appBaseUrl;
    }
}
