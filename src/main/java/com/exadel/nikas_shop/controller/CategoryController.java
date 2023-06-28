package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.CategoryDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("category")
@RequiredArgsConstructor
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    @PreAuthorize("permitAll()")
    ResponseEntity<DataResponseDto<CategoryDto>> getList() {
        return categoryService.getList();
    }

    @GetMapping("/{categoryId}")
    @PreAuthorize("permitAll()")
    ResponseEntity<CategoryDto> getCategoryById(@PathVariable("categoryId") Long categoryId) {
        return categoryService.getById(categoryId);
    }

    @PostMapping()
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    @DeleteMapping("/{categoryId}")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    void deleteById(@PathVariable("categoryId") Long categoryId) {
        categoryService.delete(categoryId);
    }

    @PutMapping("/{categoryId}")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable("categoryId") Long categoryId,
                                                       @Valid @RequestBody CategoryDto categoryDto) {
        return categoryService.update(categoryId, categoryDto);
    }
}
