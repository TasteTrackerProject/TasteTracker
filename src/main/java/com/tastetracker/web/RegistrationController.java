package com.tastetracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class RegistrationController
{
    @GetMapping("/registration")
    public String loginForm()
    {
        return "registration-form";
    }
}
