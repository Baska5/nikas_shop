package com.exadel.nikas_shop.statemachine;

import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import com.exadel.nikas_shop.enums.OrderEvent;
import com.exadel.nikas_shop.enums.OrderState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "order")
public class OrderStateMachine extends StateMachineConfigurerAdapter<OrderState, OrderEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<OrderState, OrderEvent> states) throws Exception {
        states.withStates()
                .initial(OrderState.ACTIVE)
                .states(EnumSet.allOf(OrderState.class))
                .end(OrderState.CANCELLED)
                .end(OrderState.LOST)
                .end(OrderState.COMPLETED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<OrderState, OrderEvent> transitions) throws Exception {
        transitions.withExternal().source(OrderState.ACTIVE).target(OrderState.CANCELLED).event(OrderEvent.CANCEL)
                .and()
                .withExternal().source(OrderState.ACTIVE).target(OrderState.SHIPPED).event(OrderEvent.SHIP)
                .and()
                .withExternal().source(OrderState.SHIPPED).target(OrderState.LOST).event(OrderEvent.LOSE)
                .and()
                .withExternal().source(OrderState.SHIPPED).target(OrderState.COMPLETED).event(OrderEvent.COMPLETE);
    }
}
