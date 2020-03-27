package com.sombrainc.ssm;

import com.sombrainc.ssm.events.LeadEvents;
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
    private LeadService leadService;

    @Test
    public void whenAssignSell_thenReturnSoldFromPersister(){

        assertEquals(stateMachine.getState().getId(), LeadStates.NEW);

        leadService.updateLeadStateByEvent(LeadEvents.ASSIGN);
        leadService.updateLeadStateByEvent(LeadEvents.SELL);

        assertEquals(stateMachine.getState().getId(), LeadStates.SOLD);
    }

    @Test
    public void whenNoActionPerformed_thenReturnNewFromPersister(){
        assertEquals(stateMachine.getState().getId(), LeadStates.NEW);
    }

}
