package com.sombrainc.ssm;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.service.LeadService;
import com.sombrainc.ssm.states.LeadStates;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LeadServiceTest {

    @Autowired
    private StateMachineFactory<LeadStates, LeadEvents> stateMachineFactory;

    @Autowired
    private StateMachinePersister<LeadStates, LeadEvents, String> stateMachinePersister;

    @Autowired
    private LeadService leadService;

    @Test
    public void whenAssignSellFromMultipleStateMachines_thenRestoreSold() throws Exception {
        final String leadId = RandomStringUtils.randomAlphabetic(12);

        leadService.updateLeadStateByEvent(LeadEvents.ASSIGN, leadId);
        leadService.updateLeadStateByEvent(LeadEvents.SELL, leadId);

        final StateMachine<LeadStates, LeadEvents> otherStateMachine =
                stateMachineFactory.getStateMachine();
        final StateMachine<LeadStates, LeadEvents> stateMachineStored =
                stateMachinePersister.restore(otherStateMachine, leadId);
        assertEquals(stateMachineStored.getState().getId(), LeadStates.SOLD);
    }

    @Test
    public void whenNoExplicitEventsSentAndPersist_thenRestoreEmpty() throws Exception {
        final String leadId = RandomStringUtils.randomAlphabetic(12);
        stateMachinePersister.persist(stateMachineFactory.getStateMachine(), leadId);
        final StateMachine<LeadStates, LeadEvents> restoredStateMachine =
                stateMachinePersister.restore(stateMachineFactory.getStateMachine(), leadId);
        assertEquals(restoredStateMachine.getState().getId(), LeadStates.NEW);
    }

}
