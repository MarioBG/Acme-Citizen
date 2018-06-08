
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

	/*
	 * Caso de uso 15.b: Crear chirps y borrar sus propios chirps.
	 */

	@Test
	public void driverCreateAndDelete() {
		final Object testingCreateAndDelete[][] = {
			// Casos positivos
			{
				"government", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", null
			}, {
				"tvh", "chirp title", "chirp description", null, null, null
			},
			// Casos negativos
			{
				"government", "", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", IllegalArgumentException.class
			}, // Usuario no se puede guardar un chirp sin titulo
			{
				"bank1", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", NumberFormatException.class
			}, // Usuario inexistente
		};

		for (int i = 0; i < testingCreateAndDelete.length; i++)
			this.templateCreateAndEdit((String) testingCreateAndDelete[i][0], (String) testingCreateAndDelete[i][1], (String) testingCreateAndDelete[i][2], (String) testingCreateAndDelete[i][3], (String) testingCreateAndDelete[i][4],
				(Class<?>) testingCreateAndDelete[i][5]);
	}

	private void templateCreateAndEdit(final String username, final String title, final String description, final String image, final String link, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		Chirp chirp;

		try {
			final int governmentAgentId = super.getEntityId(username);
			final GovernmentAgent governmentAgent = this.governmentAgentService.findOne(governmentAgentId);
			super.authenticate(governmentAgent.getUserAccount().getUsername());
			chirp = this.chirpService.create();
			chirp.setTitle(title);
			chirp.setContent(description);
			chirp.setImage(image);
			chirp.setLink(link);
			final Chirp saved = this.chirpService.save(chirp);
			Assert.isTrue(this.chirpService.findAll().contains(saved) && governmentAgent.getChirps().contains(saved));
			this.chirpService.delete(saved);
			Assert.isTrue(!this.chirpService.findAll().contains(saved) && !governmentAgent.getChirps().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
