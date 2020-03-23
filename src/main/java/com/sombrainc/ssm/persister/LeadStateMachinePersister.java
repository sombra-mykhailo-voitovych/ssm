package com.sombrainc.ssm.persister;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class LeadStateMachinePersister implements StateMachinePersister<LeadStates, LeadEvents, String> {

    private final Map<String, StateMachine<LeadStates, LeadEvents>> stateMap = new HashMap<>();

    @Override public void persist(StateMachine<LeadStates, LeadEvents> stateMachine,
            String contextObj) {
        stateMap.put(contextObj, stateMachine);
    }

    @Override public StateMachine<LeadStates, LeadEvents> restore(
            StateMachine<LeadStates, LeadEvents> stateMachine, String contextObj) {
        return stateMap.get(contextObj);
    }
}
