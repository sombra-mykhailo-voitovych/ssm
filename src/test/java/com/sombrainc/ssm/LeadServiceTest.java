package com.sombrainc.ssm;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.persister.LeadStateMachinePersister;
import com.sombrainc.ssm.service.LeadService;
import com.sombrainc.ssm.states.LeadStates;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LeadServiceTest {

    @Autowired
    private StateMachine<LeadStates, LeadEvents> stateMachine;

    @Autowired
    private LeadStateMachinePersister leadStateMachinePersister;

    @Autowired
    private LeadService leadService;

    @Test
    public void whenAssignSell_thenReturnSoldFromPersister(){
        final String leadId = (String) stateMachine.getExtendedState().getVariables().get("lead_id");

        leadService.updateLeadStateByEvent(LeadEvents.ASSIGN);
        leadService.updateLeadStateByEvent(LeadEvents.SELL);

        final StateMachine<LeadStates, LeadEvents> stateMachineStored =
                leadStateMachinePersister.restore(stateMachine, leadId);
        assertEquals(stateMachineStored.getState().getId(), LeadStates.SOLD);
    }

    @Test
    public void whenNoActionPerformed_thenReturnNewFromPersister(){
        final String leadId = (String) stateMachine.getExtendedState().getVariables().get("lead_id");
        final StateMachine<LeadStates, LeadEvents> stateMachineStored =
                leadStateMachinePersister.restore(stateMachine, leadId);
        assertEquals(stateMachineStored.getState().getId(), LeadStates.NEW);
    }

}
