package com.tastetracker.domain.category.dto;

import java.util.Set;

public record CategoryDto(Long id,String name, Set<String> restaurant)
{
}

