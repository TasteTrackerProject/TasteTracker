package com.tastetracker.domain.category;

import com.tastetracker.domain.category.dto.CategoryDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class CategoryService
{
    private final CategoryRepository categoryRepository;

    public List<CategoryDto> findAllCategory()
    {
        return StreamSupport.stream( categoryRepository.findAll().spliterator(),false )
            .map( CategoryDtoMapper::map )
            .toList();
    }

    public Optional<CategoryDto> findCategoryByName( String name )
    {
        return categoryRepository.findByNameIgnoreCase( name )
            .map( CategoryDtoMapper::map );
    }
}
