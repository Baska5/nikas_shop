package com.exadel.nikas_shop.mapper;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.ProductDto;
import com.exadel.nikas_shop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(uses = {CategoryMapper.class})
public interface ProductMapper {

    default Long productToId(Product product) {
        return product.getId();
    }

    default Product idToProduct(Long id) {
        Product product = new Product();
        product.setId(id);
        return product;
    }

    ProductDto mapProductToResponse(Product product);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    Product mapCreateToProduct(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "created", ignore = true)
    void mapUpdateToProduct(@MappingTarget Product product, ProductDto productDto);

    @Mapping(target = "data", source = "list")
    @Mapping(target = "size", source = "size")
    DataResponseDto<ProductDto> mapProductsToResponse(Integer size, List<ProductDto> list);

}
