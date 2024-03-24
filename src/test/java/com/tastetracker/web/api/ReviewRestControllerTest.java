package com.tastetracker.web.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tastetracker.domain.rating.RatingService;
import com.tastetracker.domain.review.ReviewService;
import com.tastetracker.domain.review.dto.ReviewDto;
import com.tastetracker.domain.user.UserService;
import com.tastetracker.domain.user.dto.UserDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith( SpringExtension.class )
@SpringBootTest
@AutoConfigureMockMvc

class ReviewRestControllerTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private  MockMvc mockMvc;
    @MockBean
    private  UserService userService;
    @MockBean
    private  ReviewService reviewService;
    @MockBean
    private  RatingService ratingService;


    @BeforeEach
    void setUp()
    {
        this.mockMvc = MockMvcBuilders.webAppContextSetup( this.webApplicationContext ).build();
    }

    @Test
    void givenReviewDto_whenAddReview_thenReviewAddedSuccessfully3() throws Exception {
        // Tworzenie obiektu ReviewDto
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setLogin("admin");
        reviewDto.setReviewContent("Bardzo fajna restauracja");
        reviewDto.setRatingTaste(5);
        reviewDto.setRatingService(4);
        reviewDto.setRatingAtmosphere(4);

        when(userService.findUserByLogin("admin")).thenReturn( Optional.of( new UserDto(1L,"admin")));
        when(reviewService.hasUserReviewsRestaurant(anyLong(), anyLong())).thenReturn(false);
        when(reviewService.addReviews(anyLong(), eq(reviewDto), anyString())).thenReturn(reviewDto);

        Authentication authentication =
            new UsernamePasswordAuthenticationToken( "admin", "admin", Collections.emptyList() );
        SecurityContext securityContext = mock( SecurityContext.class );
        when( securityContext.getAuthentication() ).thenReturn( authentication );
        SecurityContextHolder.setContext( securityContext );

        // Wywołanie metody kontrolera
        mockMvc.perform(post("/api/review/{restaurantId}", 1)
                            .contentType( MediaType.APPLICATION_JSON)
                            .content(asJsonString(reviewDto)))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.login").value("admin"))
            .andExpect(jsonPath("$.reviewContent").value("Bardzo fajna restauracja"))
            .andExpect(jsonPath("$.ratingTaste").value(5))
            .andExpect(jsonPath("$.ratingService").value(4))
            .andExpect(jsonPath("$.ratingAtmosphere").value(4));

        // Weryfikacja wywołań metod
        verify(userService, times(1)).findUserByLogin(anyString());
        verify(reviewService, times(1)).hasUserReviewsRestaurant(anyLong(), anyLong());
        verify(reviewService, times(1)).addReviews(anyLong(), eq(reviewDto), anyString());
    }

    private String asJsonString(Object obj) throws JsonProcessingException
    {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }
}