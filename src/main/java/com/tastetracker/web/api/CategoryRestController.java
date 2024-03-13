package com.tastetracker.web.api;

import com.tastetracker.domain.category.CategoryService;
import com.tastetracker.domain.category.dto.CategoryDto;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/api/category" )
@AllArgsConstructor
public class CategoryRestController
{
    private final CategoryService categoryService;

    @GetMapping( "/all" )
    public ResponseEntity<List<CategoryDto>> getAllCategories()
    {
        List<CategoryDto> categories =  categoryService.getAllCategories();
        return ResponseEntity.ok( categories );
    }
}
