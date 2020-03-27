package com.sombrainc.ssm.service;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

/**
 * This service is used to showcase how to use state machine event sending
 * and persisting current state from ordinary mvc service. Invoked from functional tests only
 * */

@Service
public class LeadService {

    private final StateMachine<LeadStates, LeadEvents> stateMachine;

    @Autowired
    public LeadService(
            StateMachine<LeadStates, LeadEvents> stateMachine) {
        this.stateMachine = stateMachine;
    }

    public void updateLeadStateByEvent(LeadEvents event){
        final boolean eventResult = stateMachine.sendEvent(event);
        if (!eventResult){
            throw new RuntimeException(String.format("event %s is not accepted", event));
        }
    }


}
