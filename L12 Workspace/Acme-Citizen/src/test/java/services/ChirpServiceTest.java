
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Chirp;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ChirpServiceTest extends AbstractTest {

	@Autowired
	private ChirpService	chirpService;


	// Test ------------------------------------------

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
			}, // Usuario no registrado
			{
				"bank1", "chirp title", "chirp description", "http://localhost/Acme-Citizen/images/logo.png", "http://localhost/Acme-Citizen/images/logo.png", IllegalArgumentException.class
			}, // Usuario no autenticado
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
