package com.sombrainc.ssm.config;

import com.sombrainc.ssm.actions.IncreaseFailedEngageAttemptsAction;
import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.guards.FailedAttemptsGuard;
import com.sombrainc.ssm.listeners.CustomStateMachineListener;
import com.sombrainc.ssm.persister.StateMachineContextPersister;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory
public class LeadStateMachineConfiguration extends
        EnumStateMachineConfigurerAdapter<LeadStates, LeadEvents> {

    @Override public void configure(StateMachineStateConfigurer<LeadStates, LeadEvents> states)
            throws Exception {
        states.
                withStates()
                .initial(LeadStates.NEW)
                .choice(LeadStates.CANCELLED_CHOICE)
                .states(EnumSet.allOf(LeadStates.class));
    }

    @Override public void configure(
            StateMachineConfigurationConfigurer<LeadStates, LeadEvents> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(new CustomStateMachineListener());
    }

    @Override public void configure(
            StateMachineTransitionConfigurer<LeadStates, LeadEvents> transitions) throws Exception {
        transitions
                .withExternal().source(LeadStates.NEW).target(LeadStates.ASSIGNED).event(LeadEvents.ASSIGN)
                .and().withExternal().source(LeadStates.NEW).target(LeadStates.DEAD).event(LeadEvents.GO_AWAY)
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.NEW).event(LeadEvents.UNASSIGN)
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.CANCELLED).event(LeadEvents.CANCEL)
                .and().withExternal().source(LeadStates.CANCELLED).target(LeadStates.ASSIGNED).event(LeadEvents.ASSIGN)
                .and().withExternal().source(LeadStates.CANCELLED).target(LeadStates.CANCELLED_CHOICE)
                .event(LeadEvents.CANCEL).action(new IncreaseFailedEngageAttemptsAction())
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.SOLD).event(LeadEvents.SELL)
                .and().withChoice().source(LeadStates.CANCELLED_CHOICE)
                .first(LeadStates.DEAD, new FailedAttemptsGuard())
                .last(LeadStates.CANCELLED);
    }

    @Bean
    public StateMachinePersister<LeadStates, LeadEvents, String> persister() {
        return new DefaultStateMachinePersister<>(new StateMachineContextPersister());
    }
}
