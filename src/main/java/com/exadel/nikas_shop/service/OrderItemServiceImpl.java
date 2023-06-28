package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderItemDto;
import com.exadel.nikas_shop.entity.CartItem;
import com.exadel.nikas_shop.entity.OrderItem;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.mapper.CartItemMapper;
import com.exadel.nikas_shop.mapper.OrderItemMapper;
import com.exadel.nikas_shop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderItemServiceImpl implements OrderItemService{

    @Autowired
    private OrderItemRepository repository;
    @Autowired
    private OrderItemMapper mapper;

    @Override
    public ResponseEntity<DataResponseDto<OrderItemDto>> getList() {
        List<OrderItemDto> orderItems = repository.findAll().stream()
                .map(orderItem -> mapper.mapOrderItemToResponse(orderItem))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapOrderItemsToResponse
                        (orderItems.size(), orderItems));
    }

    @Override
    public ResponseEntity<OrderItemDto> getById(Long orderItemId) {
        Optional<OrderItem> orderItem = repository.findById(orderItemId);

        orderItem.orElseThrow(() -> new EntityNotFoundException(String.format("Order item with ID %d does not exist.", orderItemId)));

        return ResponseEntity.ok(mapper.mapOrderItemToResponse(orderItem.get()));
    }

    @Override
    public ResponseEntity<OrderItemDto> create(OrderItemDto orderItemDto) {
        OrderItem orderItem = mapper.mapCreateToOrderItem(orderItemDto);
        OrderItemDto orderItemResponseDto = mapper.mapOrderItemToResponse(repository.save(orderItem));
        return new ResponseEntity<>(orderItemResponseDto, HttpStatus.CREATED);
    }

    @Override
    public void delete(Long orderItemId) {
        Optional<OrderItem> orderItem = repository.findById(orderItemId);

        orderItem.orElseThrow(() -> new EntityNotFoundException(String.format("Order item with ID %d does not exist.", orderItemId)));

        repository.deleteById(orderItemId);
    }

    @Override
    public ResponseEntity<OrderItemDto> update(Long orderItemId, OrderItemDto orderItemDto) {
        OrderItem orderItem = repository.findById(orderItemId).orElseThrow(() -> new EntityNotFoundException(String.format("Order item with ID %d does not exist.", orderItemId)));
        mapper.mapUpdateToOrderItem(orderItem, orderItemDto);
        OrderItemDto orderItemResponseDto = mapper.mapOrderItemToResponse(repository.save(orderItem));
        return ResponseEntity.ok(orderItemResponseDto);
    }
}
