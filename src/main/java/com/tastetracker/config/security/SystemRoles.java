package com.tastetracker.config.security;

import lombok.AllArgsConstructor;



@AllArgsConstructor
public enum SystemRoles
{
    USER("USER"), ADMIN("ADMIN"), EDITOR("EDITOR");

    private String role;

    public String getRole()
    {
        return role;
    }
}
