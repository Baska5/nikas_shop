package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import org.springframework.http.ResponseEntity;


public interface CartService {

    ResponseEntity<DataResponseDto<CartDto>> getList();

    ResponseEntity<CartDto> getById(Long cartId);

    ResponseEntity<CartDto> create(CartDto cartDto);

    ResponseEntity<CartDto> delete(Long cartId);

    ResponseEntity<CartDto> update(Long cartId, CartDto cartDto);

    ResponseEntity<CartDto> submit(Long cartId);

    ResponseEntity<CartDto> cancel(Long cartId);

}
