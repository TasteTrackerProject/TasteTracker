package com.tastetracker.event;


import com.tastetracker.domain.user.dto.UserRegistrationDto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
@Getter
@Setter
public class OnRegistrationCompleteEvent extends ApplicationEvent
{
    private UserRegistrationDto userRegistrationDto;

    public OnRegistrationCompleteEvent( UserRegistrationDto userRegistrationDto )
    {
        super( userRegistrationDto );
        this.userRegistrationDto = userRegistrationDto;
    }
}
