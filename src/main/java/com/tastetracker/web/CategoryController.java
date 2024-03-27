package com.tastetracker.web;

import com.tastetracker.domain.category.CategoryService;
import com.tastetracker.domain.category.dto.CategoryDto;
import com.tastetracker.domain.restaurant.RestaurantService;
import com.tastetracker.domain.restaurant.dto.RestaurantDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Controller
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class CategoryController
{
    private final CategoryService categoryService;

    private final RestaurantService restaurantService;

    @GetMapping( "/category/{name}" )
    public String getCategory( @PathVariable String name, Model model )
    {
        Optional<CategoryDto> optionalCategory = categoryService.findCategoryByName( name );
        List<RestaurantDto> restaurants = restaurantService.findAllRestaurantByCategoryName( name );
        optionalCategory.ifPresent( category -> model.addAttribute( "heading", category.name() ) );
        model.addAttribute( "restaurants", restaurants );
        return "restaurant-listing";
    }

    @GetMapping( "/category" )
    public String getCategoryList( Model model )
    {
        List<CategoryDto> categories = categoryService.findAllCategory();
        model.addAttribute( "categories", categories );
        return "category-listing";
    }
}
