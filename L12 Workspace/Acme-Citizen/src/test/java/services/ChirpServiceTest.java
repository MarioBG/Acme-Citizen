
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Chirp;
import domain.GovernmentAgent;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpServiceTest extends AbstractTest {

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Test ------------------------------------------

	/*
	 * Caso de uso 12.f: Listar todos los chirps y ver los tres últimos en la página de inicio.
	 */

	@Test
	public void driverListAllAndLast() {
		final Object testingListAllAndLastData[][] = {

			// Casos positivos
			{
				null, null
			}
		};

		for (int i = 0; i < testingListAllAndLastData.length; i++)
			this.templateListAllAndLast((String) testingListAllAndLastData[i][0], (Class<?>) testingListAllAndLastData[i][1]);

	}

	protected void templateListAllAndLast(final String authenticate, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			final ArrayList<Chirp> lastChirps = this.chirpService.lastChirps();
			Assert.isTrue(lastChirps.size() <= 3);
			final ArrayList<Chirp> chirps = (ArrayList<Chirp>) this.chirpService.findAll();
			Collections.sort(chirps);
			if (chirps.size() > 3)
				Assert.isTrue(chirps.subList(0, 3).containsAll(lastChirps));
			else
				Assert.isTrue(chirps.containsAll(lastChirps));
			this.chirpService.findAll();
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 12.g: Listar chirps de un agente gubernamental concreto.
	 */

	@Test
	public void driverListByGovernmentAgent() {
		final Object testingListByGovernmentAgentData[][] = {

			// Casos positivos
			{
				null, "government", null
			},
			// Casos negativos
			{
				"citizen1", "citizen1", IllegalArgumentException.class
			/*
			 * Solo los agentes gubernamentales poseen chirps
			 */
			}, {
				"government", "governmentTest", NumberFormatException.class
			/*
			 * El agente gubernamental no exite
			 */
			},
		};

		for (int i = 0; i < testingListByGovernmentAgentData.length; i++)
			this.templateListByGovernmentAgent((String) testingListByGovernmentAgentData[i][0], (String) testingListByGovernmentAgentData[i][1], (Class<?>) testingListByGovernmentAgentData[i][2]);

	}

	protected void templateListByGovernmentAgent(final String authenticate, final String governmentAgentBean, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int governmentAgentId = super.getEntityId(governmentAgentBean);
			final GovernmentAgent governmentAgent = this.governmentAgentService.findOne(governmentAgentId);
			super.authenticate(authenticate);
			final Collection<Chirp> chirps = this.chirpService.findByGovernmentAgentId(governmentAgentId);
			for (final Chirp chirp : chirps)
				Assert.isTrue(chirp.getGovernmentAgent().equals(governmentAgent));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	@Test
	public void driver() {
		final Object testingCreate[][] = {
			// Casos positivos
			{
				"government", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", null
			}, {
				"tvhisperia", "chirp title", "chirp description", null, null, null
			},
			// Casos negativos
			{
				"pepitoperez", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", IllegalArgumentException.class
			}, // Usuario no
				// registrado
			{
				"bank1", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", IllegalArgumentException.class
			}, // Usuario no
				// autenticado
		};

		for (int i = 0; i < testingCreate.length; i++)
			this.templateCreateAndEdit((String) testingCreate[i][0], (String) testingCreate[i][1], (String) testingCreate[i][2], (String) testingCreate[i][3], (String) testingCreate[i][4], (Class<?>) testingCreate[i][5]);
	}

	private void templateCreateAndEdit(final String username, final String title, final String description, final String image, final String link, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		Chirp chirp;

		try {
			super.authenticate(username);
			chirp = this.chirpService.create();
			chirp.setTitle(title);
			chirp.setContent(description);
			chirp.setImage(image);
			chirp.setLink(link);

		} catch (final Throwable oops) {
			caught = oops.getClass();
			this.chirpService.flush();
		}
		this.checkExceptions(expected, caught);

	}
}
