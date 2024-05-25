package com.tastetracker.domain.category;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CategoryRepositoryTest
{
    @Autowired
    private CategoryRepository categoryRepository;

    private Category testCategory;

    @BeforeEach
    public void setUp()
    {
        testCategory = new Category();
        testCategory.setName( "Gruzińska" );
        testCategory.setBanner( "banner.png" );
        categoryRepository.save( testCategory );
    }

    @AfterEach
    public void tearDown()
    {
        categoryRepository.delete( testCategory );
    }

    @Test
    void givenCategory_whenSaved_thenCanBeFoundById()
    {
        Category savedCategory = categoryRepository.findById( testCategory.getId() )
            .orElse( null );

        assertNotNull( savedCategory );
        assertEquals( testCategory.getName(), savedCategory.getName() );
        assertEquals( testCategory.getBanner(), savedCategory.getBanner() );
    }

    @Test
    void givenCategory_whenFindByNameIgnoreCase_thenCategoryIsFound()
    {
        Category foundCategory = categoryRepository.findByNameIgnoreCase( "Gruzińska" )
            .orElse( null );

        assertNotNull( foundCategory );
        assertEquals( "Gruzińska", foundCategory.getName() );
    }

    @Test
    void givenCategory_whenExistsByName_thenReturnTrue()
    {
        boolean existsByName = categoryRepository.existsByName( "Gruzińska" );

        assertTrue( existsByName );
    }
}