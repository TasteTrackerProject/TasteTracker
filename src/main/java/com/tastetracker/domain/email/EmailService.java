package com.tastetracker.domain.email;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService
{
    private final JavaMailSender mailSender;

    public EmailService( JavaMailSender mailSender )
    {
        this.mailSender = mailSender;
    }

    @Value( "${spring.mail.username}" )
    private String applicationEmail;

    public void sendEmail( String addressee, String subject, String message )
    {
        SimpleMailMessage messageToSend = new SimpleMailMessage();
        messageToSend.setFrom( applicationEmail ) ;
        messageToSend.setTo( addressee );
        messageToSend.setText( message );
        messageToSend.setSubject( subject );


        mailSender.send( messageToSend );
    }
}
