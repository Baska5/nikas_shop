package com.exadel.nikas_shop.controller;


import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderDto;
import com.exadel.nikas_shop.service.CartService;
import com.exadel.nikas_shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/list")
    @PreAuthorize("@securityAuthService.hasAccess('admin')")
    ResponseEntity<DataResponseDto<OrderDto>> getList() {
        return orderService.getList();
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> getOrderById(@PathVariable("orderId") Long orderId) {
        return orderService.getById(orderId);
    }

    @PostMapping()
    @PreAuthorize("@securityAuthService.hasAnyAccess('user', 'staff', 'admin')")
    ResponseEntity<OrderDto> createOrder(@Valid @RequestBody OrderDto orderDto) {
        return orderService.create(orderDto);
    }

    @DeleteMapping("/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> deleteById(@PathVariable("orderId") Long orderId) {
        return orderService.delete(orderId);
    }

    @PutMapping("/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> updateOrder(@PathVariable("orderId") Long orderId,
                                       @Valid @RequestBody OrderDto orderDto) {
        return orderService.update(orderId, orderDto);
    }

    @PatchMapping("/cancel/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> cancelOrder(@PathVariable("orderId") Long orderId) {
        return orderService.cancel(orderId);
    }

    @PatchMapping("/ship/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> shipOrder(@PathVariable("orderId") Long orderId) {
        return orderService.ship(orderId);
    }

    @PatchMapping("/lose/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> loseOrder(@PathVariable("orderId") Long orderId) {
        return orderService.lose(orderId);
    }

    @PatchMapping("/complete/{orderId}")
    @PreAuthorize("@securityAuthService.isOrderOwner(#orderId)")
    ResponseEntity<OrderDto> completeOrder(@PathVariable("orderId") Long orderId) {
        return orderService.complete(orderId);
    }

}
