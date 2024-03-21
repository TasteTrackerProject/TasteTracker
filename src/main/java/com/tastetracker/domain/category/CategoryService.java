package com.tastetracker.domain.category;

import com.tastetracker.domain.category.dto.CategoryDto;
import com.tastetracker.domain.category.dto.NewCategorySaveDto;
import com.tastetracker.exception.CategoryAlreadyExsistsException;
import com.tastetracker.functions.string.StringCustomFunctions;
import com.tastetracker.storage.FileStorageService;
import jakarta.transaction.Transactional;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor( access = AccessLevel.PUBLIC )
public class CategoryService
{
    private final CategoryRepository categoryRepository;
    private final FileStorageService fileStorageService;

    public List<CategoryDto> findAllCategory()
    {
        return StreamSupport.stream( categoryRepository.findAll().spliterator(), false )
            .map( CategoryDtoMapper::map )
            .toList();
    }

    public Optional<CategoryDto> findCategoryByName( String name )
    {
        return categoryRepository.findByNameIgnoreCase( name )
            .map( CategoryDtoMapper::map );
    }

    @Transactional
    @SneakyThrows
    public CategoryDto addNewCategory( NewCategorySaveDto dto )
    {
        String newCategoryName = StringCustomFunctions.formatTextFirstLetterBig( dto.getName() );

        if ( categoryRepository.existsByName( newCategoryName ) )
        {
            throw new CategoryAlreadyExsistsException( "Błąd! Kategoria - " + newCategoryName + ", znajduje się już w systemie!" );
        }

        Category categoryToSave = new Category();
        categoryToSave.setName( newCategoryName );

        if ( dto.getBanner() != null && !dto.getBanner().isEmpty() )
        {
            String savedImagePath = fileStorageService.saveCategoryBannerImage( dto.getBanner() );
            categoryToSave.setBanner( savedImagePath );
        }

        Category savedCategory = categoryRepository.save( categoryToSave );

        return CategoryDto.builder()
            .name( savedCategory.getName() )
            .build();

    }

    public List<CategoryDto> getAllCategories()
    {
        return StreamSupport.stream( categoryRepository.findAll().spliterator(), false )
            .map( CategoryDtoMapper::map )
            .toList();
    }
}
