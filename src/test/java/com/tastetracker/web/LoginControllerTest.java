package com.tastetracker.web;

import com.tastetracker.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import( SecurityConfig.class )
@WebMvcTest( LoginController.class )
class LoginControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnLoginForGetLoginForm()
        throws Exception
    {
        this.mockMvc
            .perform( get( "/login" ) )
            .andExpect( status().isOk() )
            .andExpect( view().name( "login-form" ) );
    }
}