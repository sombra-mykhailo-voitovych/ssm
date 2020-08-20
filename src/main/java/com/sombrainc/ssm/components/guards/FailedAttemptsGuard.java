package com.sombrainc.ssm.components.guards;

import com.sombrainc.ssm.components.events.LeadEvents;
import com.sombrainc.ssm.components.states.LeadStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.guard.Guard;

public class FailedAttemptsGuard implements Guard<LeadStates, LeadEvents> {

  @Override
  public boolean evaluate(StateContext<LeadStates, LeadEvents> context) {
    final int failedAttempts =
      (int) context.getExtendedState().getVariables()
        .getOrDefault("failedAttempts", 0);
    return failedAttempts >= 3;
  }

}
