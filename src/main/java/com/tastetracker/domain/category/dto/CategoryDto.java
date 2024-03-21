package com.tastetracker.domain.category.dto;


import lombok.Builder;

import java.util.Set;

@Builder
public record CategoryDto( Long id, String name, Set<String> restaurant, String banner )
{
}