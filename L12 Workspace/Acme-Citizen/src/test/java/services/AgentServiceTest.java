package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Agent;
import forms.BankAgentForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class AgentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private BankAgentService agentService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 3.1: Register to the system as an agent.
	 */

	@Test
	public void driverRegister() {
		final Object testingRegisterData[][] = {

				// Casos positivos
				{ null, true, null },
				// Casos negativos
				{ null, false, IllegalArgumentException.class }, /*
																 * No se puede
																 * registrar si
																 * no acepta los
																 * términos
																 */
				{ "agent1", true, IllegalArgumentException.class }, /*
																	 * Un
																	 * usuario
																	 * autenticado
																	 * no puede
																	 * registrarse
																	 */
		};

		for (int i = 0; i < testingRegisterData.length; i++)
			this.templateRegister((String) testingRegisterData[i][0],
					(boolean) testingRegisterData[i][1],
					(Class<?>) testingRegisterData[i][2]);

	}

	protected void templateRegister(String authenticate, boolean acceptTerms,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Agent agent = agentService.create();
			BankAgentForm bankAgentForm = agentService.construct(agent);
			bankAgentForm.setName("Agent name");
			bankAgentForm.setSurname("Agent surname");
			bankAgentForm.setAddress("Agent address");
			bankAgentForm.setEmail("email@agent.com");
			bankAgentForm.setPhone("+1234");
			bankAgentForm.setUsername("Agent username");
			bankAgentForm.setPassword("Agent password");
			bankAgentForm.setRepeatPassword("Agent password");
			bankAgentForm.setTermsAndConditions(acceptTerms);
			Agent agent2 = agentService.reconstruct(bankAgentForm, null);
			agentService.save(agent2);
			agentService.flush();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
			agentService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
