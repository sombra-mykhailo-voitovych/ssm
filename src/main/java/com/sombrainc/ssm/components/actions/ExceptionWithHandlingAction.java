package com.sombrainc.ssm.components.actions;

import com.sombrainc.ssm.components.events.LeadEvents;
import com.sombrainc.ssm.components.states.LeadStates;
import org.springframework.statemachine.StateContext;
import org.springframework.statemachine.action.Action;

public class ExceptionWithHandlingAction implements Action<LeadStates, LeadEvents> {
  @Override
  public void execute(StateContext<LeadStates, LeadEvents> context) {
    try {
      throw new RuntimeException("Oops something happened");
    } catch (RuntimeException ex) {
      context.getStateMachine().setStateMachineError(ex);
    }

  }
}
