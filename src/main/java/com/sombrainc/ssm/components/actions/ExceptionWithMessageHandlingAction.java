package com.sombrainc.ssm.components.actions;

import com.sombrainc.ssm.components.events.LeadEvents;
import com.sombrainc.ssm.components.states.LeadStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ExceptionWithMessageHandlingAction implements Action<LeadStates, LeadEvents> {
  @Override
  public void execute(StateContext<LeadStates, LeadEvents> context) {
    try {
      throw new RuntimeException("action cannot be performed");
    } catch (RuntimeException ex) {
      context.getExtendedState().getVariables().put("error", ex);
    }
  }
}
