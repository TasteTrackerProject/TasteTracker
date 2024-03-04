package com.tastetracker.domain.email;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Service
public class EmailServiceImpl implements EmailService
{
    private final JavaMailSender mailSender;

    public EmailServiceImpl( JavaMailSender mailSender )
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
    public void sendDynamicEmail( String addressee, String subject, String templatePath, String dynamicText )
        throws MessagingException, IOException
    {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper( message, true, StandardCharsets.UTF_8.name() );

        helper.setFrom( applicationEmail );
        helper.setTo( addressee );
        helper.setSubject( subject );


        ClassPathResource resource = new ClassPathResource( templatePath );
        String htmlContent = new String( resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8 );

        htmlContent = htmlContent.replace("{dynamicText}", dynamicText );

        helper.setText( htmlContent, true );

        mailSender.send( message );
    }
}
