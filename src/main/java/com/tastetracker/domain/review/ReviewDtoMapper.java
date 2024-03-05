package com.tastetracker.domain.review;

import com.tastetracker.domain.rating.Rating;
import com.tastetracker.domain.review.dto.ReviewDto;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReviewDtoMapper
{
    static ReviewDto map( Review review, List<Rating> ratings){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm");
        int ratingTaste = ratings.stream().mapToInt( Rating::getRatingTaste ).sum();
        int ratingAtmosphere = ratings.stream().mapToInt( Rating::getRatingAtmosphere ).sum();
        int ratingService = ratings.stream().mapToInt( Rating::getRatingService ).sum();
        return new ReviewDto(
            review.getUser().getLogin(),
            review.getContent(),
            review.getCreatedAt().format( formatter ),
            ratingTaste,
            ratingAtmosphere,
            ratingService
        );
    }
}
