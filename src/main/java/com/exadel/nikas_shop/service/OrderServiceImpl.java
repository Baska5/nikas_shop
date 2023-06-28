package com.exadel.nikas_shop.service;

import com.exadel.nikas_shop.dto.CartDto;
import com.exadel.nikas_shop.dto.DataResponseDto;
import com.exadel.nikas_shop.dto.OrderDto;
import com.exadel.nikas_shop.entity.Cart;
import com.exadel.nikas_shop.entity.Order;
import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.enums.OrderEvent;
import com.exadel.nikas_shop.enums.OrderState;
import com.exadel.nikas_shop.exception.exceptions.EntityNotFoundException;
import com.exadel.nikas_shop.exception.exceptions.InvalidStateTransitionException;
import com.exadel.nikas_shop.mapper.CartMapper;
import com.exadel.nikas_shop.mapper.OrderMapper;
import com.exadel.nikas_shop.repository.CartRepository;
import com.exadel.nikas_shop.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.jaxb.SpringDataJaxb;
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
public class OrderServiceImpl implements OrderService {

    public static final String ORDER_ID_HEADER = "order_id";

    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderMapper mapper;
    private final StateMachineFactory<OrderState, OrderEvent> stateMachineFactory;
    private final OrderStateChangeInterceptor orderStateChangeInterceptor;

    @Override
    public ResponseEntity<DataResponseDto<OrderDto>> getList() {
        List<OrderDto> orders = repository.findAll().stream()
                .map(order -> mapper.mapOrderToResponse(order))
                .collect(Collectors.toList());
        return ResponseEntity.ok
                (mapper.mapOrdersToResponse
                        (orders.size(), orders));
    }

    @Override
    public ResponseEntity<OrderDto> getById(Long orderId) {
        Optional<Order> order = repository.findById(orderId);

        order.orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));

        return ResponseEntity.ok(mapper.mapOrderToResponse(order.get()));
    }

    @Override
    public ResponseEntity<OrderDto> create(OrderDto orderDto) {
        Order order = mapper.mapCreateToOrder(orderDto);
        order.setStatus(OrderState.ACTIVE);
        OrderDto orderResponseDto = mapper.mapOrderToResponse(repository.save(order));
        return new ResponseEntity<>(orderResponseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderDto> delete(Long orderId) {
        Optional<Order> order = repository.findById(orderId);
        order.orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        OrderDto orderResponseDto = mapper.mapOrderToResponse(order.get());
        repository.deleteById(orderId);
        return ResponseEntity.ok(orderResponseDto);
    }

    @Override
    public ResponseEntity<OrderDto> update(Long orderId, OrderDto orderDto) {
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        mapper.mapUpdateToOrder(order, orderDto);
        OrderDto orderResponseDto = mapper.mapOrderToResponse(repository.save(order));
        return ResponseEntity.ok(orderResponseDto);
    }

    @Override
    public ResponseEntity<OrderDto> cancel(Long orderId) {
        StateMachine<OrderState, OrderEvent> sm = build(orderId);
        if (!sendEvent(orderId, sm, OrderEvent.CANCEL)) {
            throw new InvalidStateTransitionException(String.format("Can't change order's state from %s to %s", sm.getState().getId(), OrderState.CANCELLED));
        }
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        return ResponseEntity.ok(mapper.mapOrderToResponse(order));
    }

    @Override
    public ResponseEntity<OrderDto> ship(Long orderId) {
        StateMachine<OrderState, OrderEvent> sm = build(orderId);
        if (!sendEvent(orderId, sm, OrderEvent.SHIP)) {
            throw new InvalidStateTransitionException(String.format("Can't change order's state from %s to %s", sm.getState().getId(), OrderState.SHIPPED));
        }
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        return ResponseEntity.ok(mapper.mapOrderToResponse(order));
    }

    @Override
    public ResponseEntity<OrderDto> lose(Long orderId) {
        StateMachine<OrderState, OrderEvent> sm = build(orderId);
        if (!sendEvent(orderId, sm, OrderEvent.LOSE)) {
            throw new InvalidStateTransitionException(String.format("Can't change order's state from %s to %s", sm.getState().getId(), OrderState.LOST));
        }
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        return ResponseEntity.ok(mapper.mapOrderToResponse(order));
    }

    @Override
    public ResponseEntity<OrderDto> complete(Long orderId) {
        StateMachine<OrderState, OrderEvent> sm = build(orderId);
        if (!sendEvent(orderId, sm, OrderEvent.COMPLETE)) {
            throw new InvalidStateTransitionException(String.format("Can't change order's state from %s to %s", sm.getState().getId(), OrderState.COMPLETED));
        }
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        return ResponseEntity.ok(mapper.mapOrderToResponse(order));
    }

    private boolean sendEvent(Long orderId, StateMachine<OrderState, OrderEvent> sm, OrderEvent event) {
        Message<OrderEvent> msg = MessageBuilder.withPayload(event)
                .setHeader(ORDER_ID_HEADER, orderId)
                .build();
        return sm.sendEvent(msg);
    }

    private StateMachine<OrderState, OrderEvent> build(Long orderId) {
        Order order = repository.findById(orderId).orElseThrow(() -> new EntityNotFoundException(String.format("Order with ID %d does not exist.", orderId)));
        StateMachine<OrderState, OrderEvent> sm = stateMachineFactory.getStateMachine(Long.toString(order.getId()));
        sm.stop();
        sm.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(orderStateChangeInterceptor);
                    sma.resetStateMachine(new DefaultStateMachineContext<>(order.getStatus(), null, null, null));
                });
        sm.start();
        return sm;
    }
}
