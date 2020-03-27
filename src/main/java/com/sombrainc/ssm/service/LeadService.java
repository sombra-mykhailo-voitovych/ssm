package com.sombrainc.ssm.service;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Service;

/**
 * This service is used to showcase how to use state machine event sending
 * and persisting current state from ordinary mvc service. Invoked from functional tests only
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

    public void updateLeadStateByEvent(LeadEvents event, String leadId) throws Exception {
        final StateMachine<LeadStates, LeadEvents> someStateMachine =
                stateMachineFactory.getStateMachine();
        final StateMachine<LeadStates, LeadEvents> stateMachine =
                stateMachinePersister.restore(someStateMachine, leadId);
        final boolean eventResult = stateMachine.sendEvent(event);
        if(eventResult){
            stateMachinePersister.persist(stateMachine, leadId);
        }
    }


}
