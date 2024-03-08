package com.tastetracker.web.admin;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AdministrationPanelController
{
    public static final String NOTIFICATION_ATTRIBUTE = "notyfication";

    @GetMapping( "/admin" )
    public String getAdministrationPanelForm()
    {
        return "admin/index-administration-panel";
    }


    @GetMapping( "admin/add-restaurant" )
    public String addRestaurantForm()
    {
        return "admin/restaurant-add-form";
    }
}
