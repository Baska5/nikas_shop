package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("cart")
@RequiredArgsConstructor
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<DataResponseDto<CartDto>> getList() {
        return cartService.getList();
    }

    @GetMapping("/{cartId}")
    @PreAuthorize("@securityAuthService.isCartOwner(#cartId)")
    ResponseEntity<CartDto> getCartById(@PathVariable("cartId") Long cartId) {
        return cartService.getById(cartId);
    }

    @PostMapping()
    @PreAuthorize("@securityAuthService.hasAnyAccess('user', 'staff', 'admin')")
    ResponseEntity<CartDto> createCart(@Valid @RequestBody CartDto cartDto) {
        return cartService.create(cartDto);
    }

    @DeleteMapping("/{cartId}")
    @PreAuthorize("@securityAuthService.isCartOwner(#cartId)")
    ResponseEntity<CartDto> deleteById(@PathVariable("cartId") Long cartId) {
        return cartService.delete(cartId);
    }

    @PutMapping("/{cartId}")
    @PreAuthorize("@securityAuthService.isCartOwner(#cartId)")
    ResponseEntity<CartDto> updateCart(@PathVariable("cartId") Long cartId,
                                       @Valid @RequestBody CartDto cartDto) {
        return cartService.update(cartId, cartDto);
    }

    @PatchMapping("/submit/{cartId}")
    @PreAuthorize("@securityAuthService.isCartOwner(#cartId)")
    ResponseEntity<CartDto> submitCart(@PathVariable("cartId") Long cartId) {
        return cartService.submit(cartId);
    }


    @PatchMapping("/cancel/{cartId}")
    @PreAuthorize("@securityAuthService.isCartOwner(#cartId)")
    ResponseEntity<CartDto> cancelCart(@PathVariable("cartId") Long cartId) {
        return cartService.cancel(cartId);
    }
}
