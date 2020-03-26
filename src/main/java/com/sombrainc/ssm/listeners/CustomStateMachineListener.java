package com.sombrainc.ssm.listeners;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Optional;

@Component
public class CustomStateMachineListener extends StateMachineListenerAdapter<LeadStates, LeadEvents> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomStateMachineListener.class);

    @Override public void stateChanged(State<LeadStates, LeadEvents> from,
            State<LeadStates, LeadEvents> to) {
        LOGGER.info(String.format("Change state from %s to %s", Optional.ofNullable(from).map(State::getId).map(
                Objects::toString).orElse("Start point"), to.getId()));
    }

}
