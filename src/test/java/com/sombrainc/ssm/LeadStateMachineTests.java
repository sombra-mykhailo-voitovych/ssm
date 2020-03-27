package com.sombrainc.ssm;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
class LeadStateMachineTests {

	@Autowired
	private StateMachineFactory<LeadStates, LeadEvents> stateMachineFactory;

	@Test
	void whenNewAssignCancel3Times_thenVerifyIsDead() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
				.defaultAwaitTime(10)
				.stateMachine(stateMachine)
				.step()
				.expectState(LeadStates.NEW)
				.expectStateChanged(0)
				.and()
				.step()
				.sendEvent(LeadEvents.ASSIGN)
				.expectState(LeadStates.ASSIGNED)
				.expectStateChanged(1)
				.and()
				.step()
				.sendEvent(LeadEvents.CANCEL)
				.expectStateChanged(1)
				.expectState(LeadStates.CANCELLED)
				.and()
				.step()
				.sendEvent(LeadEvents.CANCEL)
				.sendEvent(LeadEvents.CANCEL)
				.sendEvent(LeadEvents.CANCEL)
				.expectStateChanged(3)
				.expectState(LeadStates.DEAD)
				.and()
				.build();

		testScenario.test();
	}


	@Test
	void whenNewAssignSell_thenVerifyIsSold() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.step()
						.sendEvent(LeadEvents.SELL)
						.expectState(LeadStates.SOLD)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewGoAway_thenVerifyIsDead() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.GO_AWAY)
						.expectState(LeadStates.DEAD)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewSell_thenVerifyEventNotAccepted() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.SELL)
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.expectEventNotAccepted(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewAssign_thenVerifyIsAssigned() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewAssignUnassign_thenVerifyIsNew() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.step()
						.sendEvent(LeadEvents.UNASSIGN)
						.expectState(LeadStates.NEW)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewAssignCancel_thenVerifyIsCancelled() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.step()
						.sendEvent(LeadEvents.CANCEL)
						.expectState(LeadStates.CANCELLED)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

	@Test
	void whenNewAssignCancelAssign_thenVerifyIsAssigned() throws Exception {
		final StateMachine<LeadStates, LeadEvents> stateMachine =
				stateMachineFactory.getStateMachine();
		final StateMachineTestPlan<LeadStates, LeadEvents> testScenario =
				StateMachineTestPlanBuilder.<LeadStates, LeadEvents>builder()
						.defaultAwaitTime(10)
						.stateMachine(stateMachine)
						.step()
						.expectState(LeadStates.NEW)
						.expectStateChanged(0)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.step()
						.sendEvent(LeadEvents.CANCEL)
						.expectState(LeadStates.CANCELLED)
						.expectStateChanged(1)
						.and()
						.step()
						.sendEvent(LeadEvents.ASSIGN)
						.expectState(LeadStates.ASSIGNED)
						.expectStateChanged(1)
						.and()
						.build();

		testScenario.test();
	}

}
