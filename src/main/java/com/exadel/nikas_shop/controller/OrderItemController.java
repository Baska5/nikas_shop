package com.exadel.nikas_shop.controller;

import com.exadel.nikas_shop.dto.CartItemDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderItemDto;
import com.exadel.nikas_shop.service.CartItemService;
import com.exadel.nikas_shop.service.OrderItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("order-item")
@RequiredArgsConstructor
public class OrderItemController {
    @Autowired
    private OrderItemService orderItemService;

    @GetMapping("/list")
    ResponseEntity<DataResponseDto<OrderItemDto>> getList() {
        return orderItemService.getList();
    }

    @GetMapping("/{orderItemId}")
    ResponseEntity<OrderItemDto> getOrderItemById(@PathVariable("orderItemId") Long orderItemId) {
        return orderItemService.getById(orderItemId);
    }

    @PostMapping()
    ResponseEntity<OrderItemDto> createOrderItem(@Valid @RequestBody OrderItemDto orderItemDto) {
        return orderItemService.create(orderItemDto);
    }

    @DeleteMapping("/{orderItemId}")
    void deleteById(@PathVariable("orderItemId") Long orderItemId) {
        orderItemService.delete(orderItemId);
    }

    @PutMapping("/{orderItemId}")
    ResponseEntity<OrderItemDto> updateOrderItem(@PathVariable("orderItemId") Long orderItemId,
                                               @Valid @RequestBody OrderItemDto orderItemDto) {
        return orderItemService.update(orderItemId, orderItemDto);
    }
}