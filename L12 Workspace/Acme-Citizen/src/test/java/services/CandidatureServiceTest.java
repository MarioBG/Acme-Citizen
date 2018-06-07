
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Candidature;
import domain.Election;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CandidatureServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CandidatureService	candidatureService;

	@Autowired
	private ElectionService		electionService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 14.h: Presentar una candidatura a elecciones que estén entre la fecha de apertura de candidaturas y un día antes de la de celebración. Una candidatura podrá ser editada y miembros añadidos a ella hasta un día antes de la celebración de
	 * elecciones y solo puede ser editada por ciudadanos que sean candidatos a ella. Las candidaturas se pueden retirar hasta el día de las elecciones.
	 */

	@Test
	public void driverCreateEditAndDelete() {
		final Object testingCreateEditAndDeleteData[][] = {

			// Casos positivos
			{
				"citizen1", "election2", "http://www.podemos.com", "Podemos", "http://www.podemos.com/podemos.png", null
			},
			// Casos negativos
			{
				"citizen2", "election1", "http://www.podemos.com", "Podemos", "http://www.podemos.com/podemos.png", IllegalArgumentException.class
			/*
			 * No se puede crear una candidatura para un rango de fecha no válido
			 */
			}, {
				"citizen2", "election2", "http://www.podemos.com", "Podemos", "", IllegalArgumentException.class
			/*
			 * El campo party logo es obligatorio
			 */
			}
		};

		for (int i = 0; i < testingCreateEditAndDeleteData.length; i++)
			this.templateCreateEditAndDelete((String) testingCreateEditAndDeleteData[i][0], (String) testingCreateEditAndDeleteData[i][1], (String) testingCreateEditAndDeleteData[i][2], (String) testingCreateEditAndDeleteData[i][3],
				(String) testingCreateEditAndDeleteData[i][4], (Class<?>) testingCreateEditAndDeleteData[i][5]);

	}

	protected void templateCreateEditAndDelete(final String authenticate, final String electionBean, final String electoralProgram, final String description, final String partyLogo, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int electionId = super.getEntityId(electionBean);
			final Election election = this.electionService.findOne(electionId);
			super.authenticate(authenticate);
			final Candidature candidature = this.candidatureService.create(electionId);
			candidature.setElectoralProgram(electoralProgram);
			candidature.setDescription(description);
			candidature.setPartyLogo(partyLogo);
			final Candidature saved = this.candidatureService.save(candidature);
			Assert.isTrue(election.getCandidatures().contains(saved));
			saved.setDescription("Pues me cambio...");
			this.candidatureService.save(saved);
			this.candidatureService.delete(saved);
			Assert.isTrue(!election.getCandidatures().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
