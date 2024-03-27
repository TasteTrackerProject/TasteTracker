package com.tastetracker.web;

import com.tastetracker.config.security.SecurityConfig;
import com.tastetracker.domain.restaurant.RestaurantService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Import( SecurityConfig.class )
@WebMvcTest( HomeController.class )
class HomeControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturnIndexForGetHome()
        throws Exception
    {
        this.mockMvc
            .perform( get( "/" ) )
            .andExpect( status().isOk() )
            .andExpect( view().name( "index" ) )
            .andExpect( model().attributeExists( "restaurants" ) );
    }

    @Test
    void shouldReturnContactForGetContact()
        throws Exception
    {
        this.mockMvc
            .perform( get( "/contact" ) )
            .andExpect( status().isOk() )
            .andExpect( view().name( "contact" ) );
    }
}