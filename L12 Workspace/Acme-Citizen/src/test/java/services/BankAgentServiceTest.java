
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.BankAgent;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankAgentServiceTest extends AbstractTest {

	@Autowired
	private BankAgentService bankAgentService;

	// Test ------------------------------------------

	@Test
	public void driverBankAgent() {
		final Object testingRegisterBankAgent[][] = {
				// Casos positivos
				{ "government", "address1", "1234", "agent@mail.com", "agent", "656565656", null },

				// Casos negativos
				{ "", "address1", "1234", "agent@mail.com", "agent", "656565656", IllegalArgumentException.class }, // Un
																													// usuario
																													// no
																													// puede
																													// registrarse
																													// sin
																													// que
																													// lo
																													// registre
																													// el
																													// agente
																													// gubernamental

		};

		for (int i = 0; i < testingRegisterBankAgent.length; i++)
			this.templateRegisterAgent((String) testingRegisterBankAgent[i][0], (String) testingRegisterBankAgent[i][1],
					(String) testingRegisterBankAgent[i][2], (String) testingRegisterBankAgent[i][3],
					(String) testingRegisterBankAgent[i][4], (String) testingRegisterBankAgent[i][5],
					(Class<?>) testingRegisterBankAgent[i][6]);
	}

	private void templateRegisterAgent(final String username, final String address, final String bankCode,
			final String email, final String name, final String phone, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		BankAgent bankAgent = null;
		String message = "  ";

		try {
			super.authenticate(username);
			bankAgent = this.bankAgentService.create();
			message += "username: " + username;
			bankAgent.setAddress(address);
			bankAgent.setBankCode(bankCode);
			bankAgent.setEmail(email);
			bankAgent.setName(name);
			bankAgent.setPhone(phone);

		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.bankAgentService.flush();
		}
		this.checkExceptions(expected, caught, message);

	}
}
