package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Citizen;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CitizenServiceTest extends AbstractTest {

	@Autowired
	private CitizenService citizenService;

	/**
	 * Registrar un ciudadano
	 */

	@Test()
	public void driverCitizen() {
		final Object testingRegisterCitizen[][] = {
				// Casos positivos
				{ "government", "address1", "agent@mail.com", "agent", "656565656", null },

				// Casos negativos
				{ null, "address1", "agent@mail.com", "agent", "656565656", NullPointerException.class }, // No se puede
																											// registrar
																											// a un
																											// ciudadano
																											// sin que
																											// un agente
																											// gubernamental
																											// este
																											// logeado

		};

		for (int i = 0; i < testingRegisterCitizen.length; i++)
			this.templateRegisterAgent((String) testingRegisterCitizen[i][0], (String) testingRegisterCitizen[i][1],
					(String) testingRegisterCitizen[i][2], (String) testingRegisterCitizen[i][3],
					(String) testingRegisterCitizen[i][4], (Class<?>) testingRegisterCitizen[i][5]);
	}

	private void templateRegisterAgent(final String username, final String address, final String email,
			final String name, final String nif, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		Citizen citizen = null;

		try {
			super.authenticate(username);
			citizen = this.citizenService.create();
			citizen.setAddress(address);
			citizen.setEmail(email);
			citizen.setName(name);
			citizen.setNif(nif);

		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.citizenService.flush();
		}
		this.checkExceptions(expected, caught);

	}

}
