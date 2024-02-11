package com.tastetracker.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.net.http.HttpRequest;

@Controller
public class HomeController
{
    @GetMapping
    public String getHome()
    {
        return "restaurant-listing";

    }
}
