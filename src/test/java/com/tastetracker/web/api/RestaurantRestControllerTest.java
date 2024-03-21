package com.tastetracker.web.api;

import jakarta.servlet.ServletContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc
class RestaurantRestControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.webApplicationContext ).build();
    }

    @Test
    void givenWac_whenServletContext_thenItProvidesRestaurantRestController()
    {
        ServletContext servletContext = webApplicationContext.getServletContext();

        assertNotNull( servletContext );
        assertInstanceOf( MockServletContext.class, servletContext );
        assertNotNull( webApplicationContext.getBean( RestaurantRestController.class ) );
    }

    @Test
    void shouldReturnNotEmptyList()
        throws Exception
    {
        this.mockMvc.perform( get( "/api/search/allRestaurant?first={value}", "pizza" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( "application/json" ) )
            .andExpect( jsonPath( "$" ).isArray() )
            .andExpect( jsonPath( "$[0]" ).exists() );
    }

    @Test
    void shouldReturnEmptyList()
        throws Exception
    {
        this.mockMvc.perform( get( "/api/search/allRestaurant?first={value}", "kapusta" ) )
            .andDo( print() )
            .andExpect( status().isOk() )
            .andExpect( content().contentType( "application/json" ) )
            .andExpect( jsonPath( "$" ).isArray() )
            .andExpect( jsonPath( "$" ).isEmpty() );
    }
}