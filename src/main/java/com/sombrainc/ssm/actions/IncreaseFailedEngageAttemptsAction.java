package com.sombrainc.ssm.actions;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;
import org.springframework.stereotype.Component;

@Component
public class IncreaseFailedEngageAttemptsAction implements Action<LeadStates, LeadEvents> {

    @Override public void execute(StateContext<LeadStates, LeadEvents> context) {
        int failedEngageAttempts =
                (Integer) context.getExtendedState().getVariables().getOrDefault("failedAttempts", 0);
        failedEngageAttempts++;
        context.getExtendedState().getVariables().put("failedAttempts", failedEngageAttempts);

    }
}
