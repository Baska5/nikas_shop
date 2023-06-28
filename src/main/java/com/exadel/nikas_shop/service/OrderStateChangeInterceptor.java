package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.entity.Cart;
import com.exadel.nikas_shop.entity.Order;
import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.enums.OrderEvent;
import com.exadel.nikas_shop.enums.OrderState;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.repository.CartRepository;
import com.exadel.nikas_shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class OrderStateChangeInterceptor extends StateMachineInterceptorAdapter<OrderState, OrderEvent> {
    private final OrderRepository orderRepository;

    @Override
    public void preStateChange(State<OrderState, OrderEvent> state, Message<OrderEvent> message,
                               Transition<OrderState, OrderEvent> transition, StateMachine<OrderState, OrderEvent> stateMachine) {
        Optional.ofNullable(message).flatMap(msg ->
                        Optional.ofNullable((Long) msg.getHeaders().getOrDefault(OrderServiceImpl.ORDER_ID_HEADER, -1L)))
                .ifPresent(orderId -> {
                    Order order = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
                    order.setStatus(state.getId());
                    orderRepository.save(order);
                });
    }
}