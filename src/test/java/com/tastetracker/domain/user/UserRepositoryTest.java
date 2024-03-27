package com.tastetracker.domain.user;

import com.tastetracker.config.security.SystemRoles;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    private User testUser;


    @BeforeEach
    public void setUp()
    {

        UserRole userRole = userRoleRepository.findByName( SystemRoles.USER.getRole() ).orElseThrow();
        testUser = new User();
        testUser.setEmail( "example@example.com" );
        testUser.setLogin( "example" );
        testUser.setPassword( "1234" );
        testUser.getRoles().add( userRole );
        userRepository.save( testUser );
    }

    @AfterEach
    public void tearDown()
    {
        userRepository.delete( testUser );
    }

    @Test
    void givenUser_whenSaved_thenCanBeFoundById()
    {
        User savedUser = userRepository.findById( testUser.getId() ).orElse( null );

        assertNotNull( savedUser );
        assertEquals( testUser.getLogin(), savedUser.getLogin() );
        assertEquals( testUser.getEmail(), savedUser.getEmail() );
        assertEquals( testUser.getPassword(), savedUser.getPassword() );
        assertEquals( testUser.getRoles(), savedUser.getRoles() );
    }

    @Test
    void givenUser_whenUpdated_thenCanBeFoundByIdWithUpdatedData()
    {
        testUser.setLogin( "user4" );
        userRepository.save( testUser );

        User updateUser = userRepository.findById( testUser.getId() ).orElse( null );

        assertNotNull( updateUser );
        assertEquals( "user4", updateUser.getLogin() );
    }

    @Test
    void givenUser_whenFindByLogin_thenUserIsFound()
    {
        User foundUser = userRepository.findByLogin( "example" ).orElse( null );

        assertNotNull( foundUser );
        assertEquals( "example",foundUser.getLogin() );
    }
}