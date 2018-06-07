
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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

}
