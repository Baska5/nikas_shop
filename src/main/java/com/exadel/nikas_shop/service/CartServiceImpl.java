package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.*;
import com.exadel.nikas_shop.entity.Cart;
import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.exception.exceptions.InvalidStateTransitionException;
import com.exadel.nikas_shop.mapper.CartMapper;
import com.exadel.nikas_shop.mapper.OrderItemMapper;
import com.exadel.nikas_shop.mapper.OrderMapper;
import com.exadel.nikas_shop.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CartServiceImpl implements CartService {

    public static final String CART_ID_HEADER = "cart_id";

    @Autowired
    private CartRepository repository;
    @Autowired
    private CartMapper mapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;
    @Autowired
    private CartItemService cartItemService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderItemService orderItemService;
    private final StateMachineFactory<CartState, CartEvent> stateMachineFactory;
    private final CartStateChangeInterceptor cartStateChangeInterceptor;

    @Override
    public ResponseEntity<DataResponseDto<CartDto>> getList() {
        List<CartDto> carts = repository.findAll().stream()
                .map(cart -> mapper.mapCartToResponse(cart))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapCartsToResponse
                        (carts.size(), carts));
    }

    @Override
    public ResponseEntity<CartDto> getById(Long cartId) {
        Optional<Cart> cart = repository.findById(cartId);

        cart.orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));

        return ResponseEntity.ok(mapper.mapCartToResponse(cart.get()));
    }

    @Override
    public ResponseEntity<CartDto> create(CartDto cartDto) {
        Cart cart = mapper.mapCreateToCart(cartDto);
        cart.setStatus(CartState.ACTIVE);
        CartDto cartResponseDto = mapper.mapCartToResponse(repository.save(cart));
        return new ResponseEntity<>(cartResponseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<CartDto> delete(Long cartId) {
        Optional<Cart> cart = repository.findById(cartId);
        cart.orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
        CartDto cartResponseDto = mapper.mapCartToResponse(cart.get());
        repository.deleteById(cartId);
        return ResponseEntity.ok(cartResponseDto);
    }

    @Override
    public ResponseEntity<CartDto> update(Long cartId, CartDto cartDto) {
        Cart cart = repository.findById(cartId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
        mapper.mapUpdateToCart(cart, cartDto);
        CartDto cartResponseDto = mapper.mapCartToResponse(repository.save(cart));
        return ResponseEntity.ok(cartResponseDto);
    }

    @Override
    public ResponseEntity<CartDto> submit(Long cartId) {
        StateMachine<CartState, CartEvent> sm = build(cartId);
        if (!sendEvent(cartId, sm, CartEvent.SUBMIT)) {
            throw new InvalidStateTransitionException(String.format("Can't change cart's state from %s to %s", sm.getState().getId(), CartState.SUBMITTED));
        }
        Cart cart = repository.findById(cartId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
        buildOrder(cart);
        return ResponseEntity.ok(mapper.mapCartToResponse(cart));
    }

    @Override
    public ResponseEntity<CartDto> cancel(Long cartId) {
        StateMachine<CartState, CartEvent> sm = build(cartId);
        if (!sendEvent(cartId, sm, CartEvent.CANCEL)) {
            throw new InvalidStateTransitionException(String.format("Can't change cart's state from %s to %s", sm.getState().getId(), CartState.CANCELLED));
        }
        Cart cart = repository.findById(cartId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
        return ResponseEntity.ok(mapper.mapCartToResponse(cart));
    }

    private void buildOrder(Cart cart) {
        OrderDto responseOrder = orderService.create(orderMapper.mapCartToOrder(mapper.mapCartToResponse(cart))).getBody();
        DataResponseDto<CartItemDto> dataResponseDto = cartItemService.getListByCart(cart.getId()).getBody();
        dataResponseDto.getData().forEach(cartItemDto -> {
            OrderItemDto orderItemDto = orderItemMapper.mapCartItemToOrderItem(cartItemDto);
            orderItemDto.setOrderId(responseOrder.getId());
            orderItemService.create(orderItemDto);
        });
    }

    private boolean sendEvent(Long cartId, StateMachine<CartState, CartEvent> sm, CartEvent event) {
        Message<CartEvent> msg = MessageBuilder.withPayload(event)
                .setHeader(CART_ID_HEADER, cartId)
                .build();
        return sm.sendEvent(msg);
    }

    private StateMachine<CartState, CartEvent> build(Long cartId) {
        Cart cart = repository.findById(cartId).orElseThrow(() -> new EntityNotFoundException(String.format("Cart with ID %d does not exist.", cartId)));
        StateMachine<CartState, CartEvent> sm = stateMachineFactory.getStateMachine(Long.toString(cart.getId()));
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(cartStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(cart.getStatus(), null, null, null));
                });
        sm.start();
        return sm;
    }

}