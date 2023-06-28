package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.ProductDto;
import com.exadel.nikas_shop.entity.Product;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.ProductMapper;
import com.exadel.nikas_shop.repository.CategoryRepository;
import com.exadel.nikas_shop.repository.ProductRepository;
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
public class ProductServiceImpl implements ProductService {

    private final ExampleMatcher productMatcher = ExampleMatcher.matching()
            .withIgnoreCase("id")
            .withMatcher("product_name", ignoreCase());
    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper mapper;

    @Override
    public ResponseEntity<DataResponseDto<ProductDto>> getList() {
        List<ProductDto> products = repository.findAll().stream()
                .map(product -> mapper.mapProductToResponse(product))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapProductsToResponse
                        (products.size(), products));
    }

    @Override
    public ResponseEntity<ProductDto> getById(Long productId) {
        Optional<Product> product = repository.findById(productId);

        product.orElseThrow(() -> new EntityNotFoundException(String.format("Product with ID %d does not exist.", productId)));

        return ResponseEntity.ok(mapper.mapProductToResponse(product.get()));
    }

    @Override
    public ResponseEntity<ProductDto> create(ProductDto productDto) {
        Product product = mapper.mapCreateToProduct(productDto);
        ProductDto productResponseDto = mapper.mapProductToResponse(repository.save(product));
        return new ResponseEntity<>(productResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long productId) {
        Optional<Product> product = repository.findById(productId);

        product.orElseThrow(() -> new EntityNotFoundException(String.format("Product with ID %d does not exist.", productId)));

        repository.deleteById(productId);
    }

    @Override
    public ResponseEntity<ProductDto> update(Long productId, ProductDto productDto) {
        Product product = repository.findById(productId)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Product with ID %d does not exist.", productId)));
        mapper.mapUpdateToProduct(product, productDto);
        ProductDto productResponseDto = mapper.mapProductToResponse(repository.save(product));
        return ResponseEntity.ok(productResponseDto);
    }
}
