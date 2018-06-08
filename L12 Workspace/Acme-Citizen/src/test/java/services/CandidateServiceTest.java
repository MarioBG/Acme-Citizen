
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Candidate;
import domain.Candidature;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CandidateServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CandidateService	candidateService;

	@Autowired
	private CandidatureService	candidatureService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 12.i: Navegar de las elecciones a las candidaturas, y de ahí
	 * a los candidatos.
	 */

	@Test
	public void driverList() {
		final Object testingListData[][] = {

			// Casos positivos
			{
				null, "candidature1", null
			},
			// Casos negativos
			{
				null, "candidatureTest", NumberFormatException.class
			/*
			 * No se puede acceder a una candidatura que no existe
			 */
			}
		};

		for (int i = 0; i < testingListData.length; i++)
			this.templateList((String) testingListData[i][0], (String) testingListData[i][1], (Class<?>) testingListData[i][2]);

	}

	protected void templateList(final String authenticate, final String candidatureBean, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int candidatureId = super.getEntityId(candidatureBean);
			final Candidature candidature = this.candidatureService.findOne(candidatureId);
			super.authenticate(authenticate);
			if (!candidature.getCandidates().isEmpty()) {
				final Collection<Candidate> candidates = this.candidateService.findByCandidatureId(candidatureId);
				Assert.isTrue(candidature.getCandidates().containsAll(candidates));
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
