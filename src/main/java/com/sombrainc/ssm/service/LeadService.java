package com.sombrainc.ssm.service;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * This service is used to showcase how to use state machine event sending
 * and persisting current state from ordinary mvc service.
 * */

@Service
public class LeadService {

    private final StateMachinePersister<LeadStates, LeadEvents, String> stateMachinePersister;
    private final StateMachineFactory<LeadStates, LeadEvents> stateMachineFactory;

    @Autowired
    public LeadService(
            StateMachinePersister stateMachinePersister,
            StateMachineFactory<LeadStates, LeadEvents> stateMachineFactory) {
        this.stateMachinePersister = stateMachinePersister;
        this.stateMachineFactory = stateMachineFactory;
    }

    public LeadStates updateState(LeadEvents event, String leadId) throws Exception {
        final StateMachine<LeadStates, LeadEvents> stateMachine =
                stateMachinePersister.restore(stateMachineFactory.getStateMachine(), leadId);
        final LeadStates initialState = stateMachine.getState().getId();
        stateMachine.sendEvent(event);
        final LeadStates resultState = stateMachine.getState().getId();
        stateMachinePersister.persist(stateMachine, leadId);

        rethrowExceptionsIfAny(stateMachine, initialState, resultState);
        return resultState;

    }

    private void rethrowExceptionsIfAny(StateMachine<LeadStates, LeadEvents> stateMachine,
            LeadStates initialState, LeadStates resultState) {
        if (stateMachine.hasStateMachineError()) {
            throw new RuntimeException(String.format("State transition from %s to %s with error",
                    initialState.name(), resultState.name()));
        }
        final Map<Object, Object> variables = stateMachine.getExtendedState().getVariables();
        if (variables.containsKey("error") && variables.get("error") instanceof Throwable) {
            throw new RuntimeException(String.format("State transition from %s to %s with error." +
                            " Error details: %s", initialState.name(), resultState.name(),
                    ((Throwable) variables.get("error")).getMessage()));
        }
    }

}
