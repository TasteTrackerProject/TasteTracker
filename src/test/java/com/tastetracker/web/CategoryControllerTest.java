package com.tastetracker.web;

import com.tastetracker.config.security.SecurityConfig;
import com.tastetracker.domain.category.CategoryService;
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
@WebMvcTest( CategoryController.class )
class CategoryControllerTest
{
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private RestaurantService restaurantService;

    @Test
    void shouldReturnCategoryListingWithAttributeCategories()
        throws Exception
    {
        this.mockMvc
            .perform( get( "/category" ) )
            .andExpect( status().isOk() )
            .andExpect( view().name( "category-listing" ) )
            .andExpect( model().attributeExists( "categories" ) );
    }

    @Test
    void shouldReturnRestaurantListingByCategoryName()
        throws Exception
    {
        this.mockMvc
            .perform( get( "/category/{name}", "Pizza" ) )
            .andExpect( status().isOk() )
            .andExpect( view().name( "restaurant-listing" ) )
            .andExpect( model().attributeExists( "restaurants" ) );
    }
}