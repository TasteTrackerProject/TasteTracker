package com.tastetracker.domain.review;

import com.tastetracker.domain.review.dto.ReviewDto;

import java.time.format.DateTimeFormatter;

public class ReviewDtoMapper
{
    static ReviewDto map(Review review){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern( "yyyy-MM-dd HH:mm");
        return new ReviewDto(
            review.getUser().getLogin(),
            review.getContent(),
            review.getCreatedAt().format( formatter )
        );
    }
}
