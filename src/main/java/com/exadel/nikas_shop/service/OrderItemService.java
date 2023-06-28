package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderItemDto;
import org.springframework.http.ResponseEntity;

public interface OrderItemService {

    ResponseEntity<DataResponseDto<OrderItemDto>> getList();

    ResponseEntity<OrderItemDto> getById(Long orderItemId);

    ResponseEntity<OrderItemDto> create(OrderItemDto orderItemDto);

    void delete(Long orderItemId);

    ResponseEntity<OrderItemDto> update(Long orderItemId, OrderItemDto orderItemDto);

}
