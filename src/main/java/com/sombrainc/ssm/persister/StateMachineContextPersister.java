package com.sombrainc.ssm.persister;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;

import java.util.HashMap;
import java.util.Map;

public class StateMachineContextPersister implements StateMachinePersist<LeadStates, LeadEvents, String> {

    private final Map<String, StateMachineContext<LeadStates, LeadEvents>> stateMap = new HashMap<>();

    @Override public void write(StateMachineContext<LeadStates, LeadEvents> context,
            String contextObj) {
        stateMap.put(contextObj, context);
    }

    @Override public StateMachineContext<LeadStates, LeadEvents> read(String contextObj) {
        return stateMap.get(contextObj);
    }

}
