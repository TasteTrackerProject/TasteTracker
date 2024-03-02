package com.tastetracker.domain.review;

import com.tastetracker.domain.review.dto.ReviewDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService
{
    private final ReviewRepository reviewRepository;

    public List<ReviewDto> getReviewForRestaurant(Long restaurantId){
        return reviewRepository.findByRestaurant_Id( restaurantId ).stream()
            .map( ReviewDtoMapper::map )
            .toList();
    }
}
