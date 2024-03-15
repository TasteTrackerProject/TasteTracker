package com.tastetracker.domain.category.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@RequiredArgsConstructor
public class NewCategorySaveDto
{
    private String name;
    private MultipartFile banner;
}
