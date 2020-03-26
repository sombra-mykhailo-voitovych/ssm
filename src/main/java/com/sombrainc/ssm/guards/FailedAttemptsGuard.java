package com.sombrainc.ssm.guards;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;
import org.springframework.stereotype.Component;

@Component
public class FailedAttemptsGuard implements Guard<LeadStates, LeadEvents> {

    @Override public boolean evaluate(StateContext<LeadStates, LeadEvents> context) {
        final int failedAttempts =
                (int) context.getExtendedState().getVariables().getOrDefault("failedAttempts", 0);
        return failedAttempts >= 3;
    }

}
