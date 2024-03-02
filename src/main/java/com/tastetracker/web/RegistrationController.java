package com.tastetracker.web;

import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserRegistrationDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController
{
    private final UserService userService;
    @GetMapping("/registration")
    public String loginForm( Model model )
    {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto();
        model.addAttribute( "user", userRegistrationDto );
        return "registration-form";
    }

    @PostMapping("/registration")
    public String register( UserRegistrationDto userRegistrationDto )
    {
        userService.registerUserWithDefaultRole( userRegistrationDto );
        return "redirect:/registration-success";
    }

    @GetMapping("/registration-success")
    public String registrationSuccess()
    {
        return "registration-success";
    }

}
