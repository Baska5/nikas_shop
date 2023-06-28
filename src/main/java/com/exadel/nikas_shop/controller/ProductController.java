package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.ProductDto;
import com.exadel.nikas_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    @PreAuthorize("permitAll()")
    ResponseEntity<DataResponseDto<ProductDto>> getList() {
        return productService.getList();
    }

    @GetMapping("/{productId}")
    @PreAuthorize("permitAll()")
    ResponseEntity<ProductDto> getProductById(@PathVariable("productId") Long productId) {
        return productService.getById(productId);
    }

    @PostMapping()
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    ResponseEntity<ProductDto> createProduct(@Valid @RequestBody ProductDto productDto) {
        return productService.create(productDto);
    }

    @DeleteMapping("/{productId}")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    void deleteById(@PathVariable("productId") Long productId) {
        productService.delete(productId);
    }

    @PutMapping("/{productId}")
    @PreAuthorize("@securityAuthService.hasAnyAccess('admin', 'staff')")
    ResponseEntity<ProductDto> updateProduct(@PathVariable("productId") Long productId,
                                                     @Valid @RequestBody ProductDto productDto) {
        return productService.update(productId, productDto);
    }
}
