package com.sombrainc.ssm;

import com.sombrainc.ssm.events.LeadEvents;
import com.sombrainc.ssm.states.LeadStates;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class LeadStateMachineTests {

	@Autowired
	private StateMachine<LeadStates, LeadEvents> stateMachine;

	@Test
	void when3FailedEngageAttempts_thenVerifyIsDead() throws Exception {
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
				.sendEvent(LeadEvents.CANCEL)
				.sendEvent(LeadEvents.CANCEL)
				.sendEvent(LeadEvents.CANCEL)
				.expectStateChanged(1)
				.expectState(LeadStates.DEAD)

				.and()
				.build();

		testScenario.test();
	}


	@Test
	void whenCorrectFlow_thenVerifyIsSold() throws Exception {
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
	void whenNewAssign_thenVerifyIsAssigned() throws Exception {
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
	void whenNewAssignAndUnassign_thenVerifyIsNew() throws Exception {
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

}
