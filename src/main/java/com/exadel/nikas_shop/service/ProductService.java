package com.exadel.nikas_shop.service;


import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.ProductDto;
import org.springframework.http.ResponseEntity;

public interface ProductService {
    ResponseEntity<DataResponseDto<ProductDto>> getList();

    ResponseEntity<ProductDto> getById(Long productId);

    ResponseEntity<ProductDto> create(ProductDto productDto);

    void delete(Long productId);

    ResponseEntity<ProductDto> update(Long productId, ProductDto productDto);
}
