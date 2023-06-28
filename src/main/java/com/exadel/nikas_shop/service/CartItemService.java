package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import org.springframework.http.ResponseEntity;


public interface CartItemService {

    ResponseEntity<DataResponseDto<CartItemDto>> getList();

    ResponseEntity<CartItemDto> getById(Long cartItemId);

    ResponseEntity<CartItemDto> create(CartItemDto cartItemDto);

    void delete(Long cartItemId);

    ResponseEntity<CartItemDto> update(Long cartItemId, CartItemDto cartItemDto);

    ResponseEntity<DataResponseDto<CartItemDto>> getListByCart(Long cartId);

}
