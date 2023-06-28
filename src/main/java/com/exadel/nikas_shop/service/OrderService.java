package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderDto;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<DataResponseDto<OrderDto>> getList();

    ResponseEntity<OrderDto> getById(Long orderId);

    ResponseEntity<OrderDto> create(OrderDto orderDto);

    ResponseEntity<OrderDto> delete(Long orderId);

    ResponseEntity<OrderDto> update(Long orderId, OrderDto orderDto);

    ResponseEntity<OrderDto> cancel(Long orderId);

    ResponseEntity<OrderDto> ship(Long orderId);

    ResponseEntity<OrderDto> lose(Long orderId);

    ResponseEntity<OrderDto> complete(Long orderId);
}