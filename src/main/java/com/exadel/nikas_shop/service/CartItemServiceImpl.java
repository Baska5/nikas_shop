package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.entity.CartItem;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.CartItemMapper;
import com.exadel.nikas_shop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CartItemServiceImpl implements CartItemService {
    @Autowired
    private CartItemRepository repository;
    @Autowired
    private CartItemMapper mapper;
    @Override
    public ResponseEntity<DataResponseDto<CartItemDto>> getList() {
        List<CartItemDto> cartItems = repository.findAll().stream()
                .map(cartItem -> mapper.mapCartItemToResponse(cartItem))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapCartItemsToResponse
                        (cartItems.size(), cartItems));
    }

    @Override
    public ResponseEntity<CartItemDto> getById(Long cartItemId) {
        Optional<CartItem> cartItem = repository.findById(cartItemId);

        cartItem.orElseThrow(() -> new EntityNotFoundException(String.format("Cart item with ID %d does not exist.", cartItemId)));

        return ResponseEntity.ok(mapper.mapCartItemToResponse(cartItem.get()));
    }

    @Override
    public ResponseEntity<CartItemDto> create(CartItemDto cartItemDto) {
        CartItem cartItem = mapper.mapCreateToCartItem(cartItemDto);
        CartItemDto cartItemResponseDto = mapper.mapCartItemToResponse(repository.save(cartItem));
        return new ResponseEntity<>(cartItemResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long cartItemId) {
        Optional<CartItem> cartItem = repository.findById(cartItemId);

        cartItem.orElseThrow(() -> new EntityNotFoundException(String.format("Cart item with ID %d does not exist.", cartItemId)));

        repository.deleteById(cartItemId);
    }

    @Override
    public ResponseEntity<CartItemDto> update(Long cartItemId, CartItemDto cartItemDto) {
        CartItem cartItem = repository.findById(cartItemId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart item with ID %d does not exist.", cartItemId)));
        mapper.mapUpdateToCartItem(cartItem, cartItemDto);
        CartItemDto cartItemResponseDto = mapper.mapCartItemToResponse(repository.save(cartItem));
        return ResponseEntity.ok(cartItemResponseDto);
    }

    @Override
    public ResponseEntity<DataResponseDto<CartItemDto>> getListByCart(Long cartId) {
        List<CartItemDto> cartItems = repository.findByCartId(cartId).stream()
                .map(cartItem -> mapper.mapCartItemToResponse(cartItem))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapCartItemsToResponse
                        (cartItems.size(), cartItems));
    }

}
