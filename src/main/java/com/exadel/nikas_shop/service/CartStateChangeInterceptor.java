package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.entity.Cart;
import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.repository.CartRepository;
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
public class CartStateChangeInterceptor extends StateMachineInterceptorAdapter<CartState, CartEvent> {
    private final CartRepository cartRepository;

    @Override
    public void preStateChange(State<CartState, CartEvent> state, Message<CartEvent> message,
                               Transition<CartState, CartEvent> transition, StateMachine<CartState, CartEvent> stateMachine) {
        Optional.ofNullable(message).flatMap(msg ->
                        Optional.ofNullable((Long) msg.getHeaders().getOrDefault(CartServiceImpl.CART_ID_HEADER, -1L)))
                .ifPresent(cartId -> {
                    Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
                    cart.setStatus(state.getId());
                    cartRepository.save(cart);
                });
    }
}
