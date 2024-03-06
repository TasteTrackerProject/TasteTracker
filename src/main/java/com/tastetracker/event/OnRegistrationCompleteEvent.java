package com.tastetracker.event;


import com.tastetracker.domain.user.dto.UserRegistrationDto;
import org.springframework.context.ApplicationEvent;





public class OnRegistrationCompleteEvent extends ApplicationEvent
{
    private UserRegistrationDto userRegistrationDto;

    public OnRegistrationCompleteEvent( UserRegistrationDto userRegistrationDto )
    {
        super( userRegistrationDto );
        this.userRegistrationDto = userRegistrationDto;
    }

    public UserRegistrationDto getUserRegistrationDto()
    {
        return userRegistrationDto;
    }

    public void setUserRegistrationDto( UserRegistrationDto userRegistrationDto )
    {
        this.userRegistrationDto = userRegistrationDto;
    }
}
