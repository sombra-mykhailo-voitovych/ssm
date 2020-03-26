package com.sombrainc.ssm.service;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.persister.LeadStateMachinePersister;
import com.sombrainc.ssm.states.LeadStates;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

/**
 * This service is used to showcase how to use state machine event sending
 * and persisting current state from ordinary mvc service. Invoked from functional tests only
 * */

@Service
public class LeadService {

    private final LeadStateMachinePersister stateMachinePersister;
    private final StateMachine<LeadStates, LeadEvents> stateMachine;

    @Autowired
    public LeadService(
            LeadStateMachinePersister stateMachinePersister,
            StateMachine<LeadStates, LeadEvents> stateMachine) {
        this.stateMachinePersister = stateMachinePersister;
        this.stateMachine = stateMachine;

        final String leadId = RandomStringUtils.randomAlphabetic(12);
        stateMachine.getExtendedState().getVariables().put("lead_id", leadId);
        stateMachinePersister.persist(stateMachine, leadId);
    }

    public void updateLeadStateByEvent(LeadEvents event){
        final String leadId = (String) stateMachine.getExtendedState().getVariables().get("lead_id");
        final boolean eventResult = stateMachine.sendEvent(event);
        if(eventResult){
            stateMachinePersister.persist(stateMachine, leadId);
        }
    }


}
