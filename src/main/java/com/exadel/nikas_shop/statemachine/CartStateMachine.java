package com.exadel.nikas_shop.statemachine;

import com.exadel.nikas_shop.enums.CartEvent;
import com.exadel.nikas_shop.enums.CartState;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory(name = "cart")
public class CartStateMachine extends StateMachineConfigurerAdapter<CartState, CartEvent> {
    @Override
    public void configure(StateMachineStateConfigurer<CartState, CartEvent> states) throws Exception {
        states.withStates()
                .initial(CartState.ACTIVE)
                .states(EnumSet.allOf(CartState.class))
                .end(CartState.SUBMITTED)
                .end(CartState.CANCELLED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<CartState, CartEvent> transitions) throws Exception {
        transitions.withExternal().source(CartState.ACTIVE).target(CartState.SUBMITTED).event(CartEvent.SUBMIT)
                .and()
                .withExternal().source(CartState.ACTIVE).target(CartState.CANCELLED).event(CartEvent.CANCEL);
    }
}
