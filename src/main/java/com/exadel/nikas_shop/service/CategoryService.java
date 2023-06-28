package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CategoryDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import org.springframework.http.ResponseEntity;

public interface CategoryService {
    ResponseEntity<DataResponseDto<CategoryDto>> getList();

    ResponseEntity<CategoryDto> getById(Long categoryId);

    ResponseEntity<CategoryDto> create(CategoryDto categoryDto);

    void delete(Long categoryId);

    ResponseEntity<CategoryDto> update(Long categoryId, CategoryDto categoryDto);
}
