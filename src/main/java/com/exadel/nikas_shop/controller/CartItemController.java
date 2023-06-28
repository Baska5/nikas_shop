package com.exadel.nikas_shop.controller;


import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.service.CartItemService;
import com.exadel.nikas_shop.service.CartService;
import com.exadel.nikas_shop.dto.DataResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("cart-item")
@RequiredArgsConstructor
public class CartItemController {

    @Autowired
    private CartItemService cartItemService;

    @GetMapping("/list")
    ResponseEntity<DataResponseDto<CartItemDto>> getList() {
        return cartItemService.getList();
    }

    @GetMapping("/{cartItemId}")
    ResponseEntity<CartItemDto> getCartItemById(@PathVariable("cartItemId") Long cartItemId) {
        return cartItemService.getById(cartItemId);
    }

    @PostMapping()
    ResponseEntity<CartItemDto> createCartItem(@Valid @RequestBody CartItemDto cartItemDto) {
        return cartItemService.create(cartItemDto);
    }

    @DeleteMapping("/{cartItemId}")
    void deleteById(@PathVariable("cartItemId") Long cartItemId) {
        cartItemService.delete(cartItemId);
    }

    @PutMapping("/{cartItemId}")
    ResponseEntity<CartItemDto> updateCartItem(@PathVariable("cartItemId") Long cartItemId,
                                       @Valid @RequestBody CartItemDto cartItemDto) {
        return cartItemService.update(cartItemId, cartItemDto);
    }
}

