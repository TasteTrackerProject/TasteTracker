package com.tastetracker.domain.email;

import jakarta.mail.MessagingException;

import java.io.IOException;

public interface EmailService
{
    public void sendEmail( String addressee, String subject, String message );
    public void sendDynamicEmail( String addressee, String subject, String templatePath, String dynamicText )
        throws MessagingException, IOException;
}
