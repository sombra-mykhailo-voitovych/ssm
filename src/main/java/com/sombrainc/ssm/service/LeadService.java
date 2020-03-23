package com.sombrainc.ssm.service;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.persister.LeadStateMachinePersister;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Service;

import java.util.Map;

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
    }

    public void updateLeadStateByEvent(LeadEvents events, String leadId){
        final Map<Object, Object> customVariables = stateMachine.getExtendedState().getVariables();
        customVariables.put("lead-id", leadId);
        stateMachine.sendEvent(events);
        stateMachinePersister.persist(stateMachine, leadId);
    }


}
