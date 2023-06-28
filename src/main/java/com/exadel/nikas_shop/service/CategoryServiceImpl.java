package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CategoryDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.Category;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.CategoryMapper;
import com.exadel.nikas_shop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.ignoreCase;

@Service
public class CategoryServiceImpl implements CategoryService {

    private final ExampleMatcher categoryMatcher = ExampleMatcher.matching()
            .withIgnoreCase("id")
            .withMatcher("category_name", ignoreCase());
    @Autowired
    private CategoryRepository repository;
    @Autowired
    private CategoryMapper mapper;

    @Override
    public ResponseEntity<DataResponseDto<CategoryDto>> getList() {
        List<CategoryDto> categories = repository.findAll().stream()
                .map(category -> mapper.mapCategoryToResponse(category))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapCategoriesToResponse
                        (categories.size(), categories));
    }

    @Override
    public ResponseEntity<CategoryDto> getById(Long categoryId) {
        Optional<Category> category = repository.findById(categoryId);

        category.orElseThrow(() -> new EntityNotFoundException(String.format("Category with ID %d does not exist.", categoryId)));

        return ResponseEntity.ok(mapper.mapCategoryToResponse(category.get()));
    }

    @Override
    public ResponseEntity<CategoryDto> create(CategoryDto categoryDto) {
        Category category = mapper.mapCreateToCategory(categoryDto);
        CategoryDto categoryResponseDto = mapper.mapCategoryToResponse(repository.save(category));
        return new ResponseEntity<>(categoryResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long categoryId) {
        Optional<Category> category = repository.findById(categoryId);

        category.orElseThrow(() -> new EntityNotFoundException(String.format("Category with ID %d does not exist.", categoryId)));

        repository.deleteById(categoryId);
    }

    @Override
    public ResponseEntity<CategoryDto> update(Long categoryId, CategoryDto categoryDto) {
        Category category = repository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Category with ID %d does not exist.", categoryId)));
        mapper.mapUpdateToCategory(category, categoryDto);
        CategoryDto categoryResponseDto = mapper.mapCategoryToResponse(repository.save(category));
        return ResponseEntity.ok(categoryResponseDto);
    }
}
