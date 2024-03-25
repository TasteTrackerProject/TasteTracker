package com.tastetracker.web.exception.handler;

import com.tastetracker.domain.user.dto.UserRegistrationDto;
import com.tastetracker.exception.UserWithGivenEmailAlreadyExsistsException;
import com.tastetracker.exception.UserWithGivenLoginAlreadyExsistsException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler
{
    @ExceptionHandler( UserWithGivenLoginAlreadyExsistsException.class )
    @ResponseStatus( HttpStatus.CONFLICT )
    public String handleUserWithGivenLoginAlreadyExsistsException( UserWithGivenLoginAlreadyExsistsException ex, Model model )
    {
        model.addAttribute("notyfication", ex.getMessage() );
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute( "user", userRegistrationDto );
        return "registration-form";
    }

    @ExceptionHandler( UserWithGivenEmailAlreadyExsistsException.class  )
    @ResponseStatus( HttpStatus.CONFLICT )
    public String handleUserWithGivenEmailAlreadyExsistsException( UserWithGivenEmailAlreadyExsistsException ex, Model model )
    {
        model.addAttribute("notyfication", ex.getMessage() );
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute( "user", userRegistrationDto );
        return "registration-form";
    }
}
