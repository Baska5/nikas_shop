package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.CategoryDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface CategoryMapper {


    default Category idToCategory(Long id) {
        Category category = new Category();
        category.setId(id);
        return category;
    }

    CategoryDto mapCategoryToResponse(Category category);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    Category mapCreateToCategory(CategoryDto categoryDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    void mapUpdateToCategory(@MappingTarget Category category, CategoryDto categoryDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<CategoryDto> mapCategoriesToResponse(Integer size, List<CategoryDto> list);

}
