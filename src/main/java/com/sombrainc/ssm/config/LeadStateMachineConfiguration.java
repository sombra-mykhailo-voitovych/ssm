package com.sombrainc.ssm.config;

import com.sombrainc.ssm.actions.IncreaseFailedEngageAttemptsAction;
import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.guards.FailedAttemptsGuard;
import com.sombrainc.ssm.listeners.CustomStateMachineListener;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachine
public class LeadStateMachineConfiguration extends
        EnumStateMachineConfigurerAdapter<LeadStates, LeadEvents> {

//    @Autowired
//    private FailedAttemptsGuard failedAttemptsGuard;

    @Bean
    public FailedAttemptsGuard getFailedAttemptsGuard(){
        return new FailedAttemptsGuard();
    }



//    @Autowired
//    private IncreaseFailedEngageAttemptsAction increaseFailedEngageAttemptsAction;

    @Bean
    public IncreaseFailedEngageAttemptsAction getIncreaseFailedEngageAttemptsAction(){
        return new IncreaseFailedEngageAttemptsAction();
    }

//    @Autowired
//    private CustomStateMachineListener stateMachineListener;

    @Bean
    public CustomStateMachineListener getStateMachineListener(){
        return new CustomStateMachineListener();
    }

    @Override public void configure(StateMachineStateConfigurer<LeadStates, LeadEvents> states)
            throws Exception {
        states.
                withStates()
                .initial(LeadStates.NEW)
//                .exit(LeadStates.DEAD)
//                .end(LeadStates.SOLD)
//                .choice(LeadStates.CANCELLED)
//                .stateDo(LeadStates.CANCELLED, getIncreaseFailedEngageAttemptsAction())
//                .state(LeadStates.CANCELLED, getIncreaseFailedEngageAttemptsAction())
                .states(EnumSet.allOf(LeadStates.class));
    }

    @Override public void configure(
            StateMachineConfigurationConfigurer<LeadStates, LeadEvents> config) throws Exception {
        config
                .withConfiguration()
                .autoStartup(true)
                .listener(getStateMachineListener());
    }

    @Override public void configure(
            StateMachineTransitionConfigurer<LeadStates, LeadEvents> transitions) throws Exception {
        transitions
                .withExternal().source(LeadStates.NEW).target(LeadStates.ASSIGNED).event(LeadEvents.ASSIGN)
                .and().withExternal().source(LeadStates.NEW).target(LeadStates.DEAD).event(LeadEvents.GO_AWAY)
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.NEW).event(LeadEvents.UNASSIGN)
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.CANCELLED).event(LeadEvents.CANCEL)
                .and().withExternal().source(LeadStates.CANCELLED).target(LeadStates.ASSIGNED).event(LeadEvents.ASSIGN)
                .and().withExternal().source(LeadStates.ASSIGNED).target(LeadStates.SOLD).event(LeadEvents.SELL)
//                .and().withChoice().source(LeadStates.CANCELLED).first(LeadStates.CANCELLED, getFailedAttemptsGuard()).last(LeadStates.DEAD);
                .and().withExternal().source(LeadStates.CANCELLED).target(LeadStates.DEAD).event(LeadEvents.CANCEL).guard(getFailedAttemptsGuard())
//                /*.action(getIncreaseFailedEngageAttemptsAction())*/.guard(getFailedAttemptsGuard())
//
                .and().withInternal().source(LeadStates.CANCELLED).action(getIncreaseFailedEngageAttemptsAction());
    }
}
