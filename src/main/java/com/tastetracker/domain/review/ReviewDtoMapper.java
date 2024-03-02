package com.tastetracker.domain.review;

import com.tastetracker.domain.review.dto.ReviewDto;

public class ReviewDtoMapper
{
    static ReviewDto map(Review review){
        return new ReviewDto(
            review.getUser().getLogin(),
            review.getContent()
        );
    }
}
