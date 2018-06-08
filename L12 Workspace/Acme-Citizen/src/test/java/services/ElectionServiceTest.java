
package services;

import java.text.SimpleDateFormat;
import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Election;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ElectionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private ElectionService		electionService;

	@Autowired
	private CandidatureService	candidatureService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 12.h: Listar las elecciones registradas en el sistema, sus candidaturas y sus
	 * resultados.
	 * 
	 * Caso de uso 12.j: Mostrar las elecciones registradas en el sistema, con sus resultados mostrados
	 * en la misma página que el resto de detalles.
	 */

	@Test
	public void driverListAndListCandidatures() {
		final Object testingListAndListCandidaturesData[][] = {

			// Casos positivos
			{
				null, null
			}
		};

		for (int i = 0; i < testingListAndListCandidaturesData.length; i++)
			this.templateListAndListCandidatures((String) testingListAndListCandidaturesData[i][0], (Class<?>) testingListAndListCandidaturesData[i][1]);

	}

	protected void templateListAndListCandidatures(final String authenticate, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			final Collection<Election> elections = this.electionService.findAll();
			if (!elections.isEmpty()) {
				final Election election = this.electionService.findOne(elections.iterator().next().getId());
				if (!election.getCandidatures().isEmpty())
					this.candidatureService.findByElectionId(election.getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 15.l: Crear elecciones si tiene este permiso.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

			// Casos positivos
			{
				"government", "Esto es una descripción", "10/12/2018", "10/12/2020", null
			},
			// Casos negativos
			{
				"government", "Esto es una descripción", "10/12/2020", "10/12/2018", IllegalArgumentException.class
			/*
			 * La fecha de candidature debe de ser anterior a la fecha de celebración
			 */
			}, {
				"tvhisperia", "Esto es una descripción", "10/12/2018", "10/12/2020", IllegalArgumentException.class
			/*
			 * Solo pueden crear elecciones los agentes gubernamentales que puedan
			 */
			},
		};

		for (int i = 0; i < testingCreateData.length; i++)
			this.templateCreate((String) testingCreateData[i][0], (String) testingCreateData[i][1], (String) testingCreateData[i][2], (String) testingCreateData[i][3], (Class<?>) testingCreateData[i][4]);

	}

	protected void templateCreate(final String authenticate, final String description, final String candidatureString, final String celebrationString, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			final Election election = this.electionService.create();
			election.setDescription(description);
			final SimpleDateFormat smf = new SimpleDateFormat("dd/MM/yyyy");
			election.setCandidatureDate(smf.parse(candidatureString));
			election.setCelebrationDate(smf.parse(celebrationString));
			final Election saved = this.electionService.save(election);
			Assert.isTrue(this.electionService.findAll().contains(saved) && saved.getGovernmentAgent().getElections().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
