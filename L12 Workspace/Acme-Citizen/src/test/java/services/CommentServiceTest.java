
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Candidature;
import domain.Comment;
import domain.Election;
import domain.Petition;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CommentServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private CommentService		commentService;

	@Autowired
	private PetitionService		petitionService;

	@Autowired
	private ElectionService		electionService;

	@Autowired
	private CandidatureService	candidatureService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 14.e: Comentar en cualquier petición que no esté marcada como resuelta.
	 */

	@Test
	public void driverCreateInPetition() {
		final Object testingCreateInPetitionData[][] = {

			// Casos positivos
			{
				"citizen1", "petition1", "Texto", "", null
			},
			// Casos negativos
			{
				"citizen1", "petitionTest", "Quiero decir...", "http://www.google.es/", NumberFormatException.class
			/*
			 * No se puede crear un comentario para una petición que no existe.
			 */
			}, {
				"government", "petition1", "", "", IllegalArgumentException.class
			/*
			 * El campo texto no puede ser blanco.
			 */
			}
		};

		for (int i = 0; i < testingCreateInPetitionData.length; i++)
			this.templateCreateInPetition((String) testingCreateInPetitionData[i][0], (String) testingCreateInPetitionData[i][1], (String) testingCreateInPetitionData[i][2], (String) testingCreateInPetitionData[i][3],
				(Class<?>) testingCreateInPetitionData[i][4]);

	}

	protected void templateCreateInPetition(final String authenticate, final String petitionBean, final String text, final String picture, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int petitionId = super.getEntityId(petitionBean);
			final Petition petition = this.petitionService.findOne(petitionId);
			super.authenticate(authenticate);
			Assert.isTrue(petition.getResolved() == false);
			final Comment comment = this.commentService.create(petitionId, null);
			comment.setText(text);
			comment.setPicture(picture);
			final Comment saved = this.commentService.save(comment);
			Assert.isTrue(petition.getComments().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.f: Comentar en cualquier elección cuya fecha de celebración ya haya pasado.
	 */

	@Test
	public void driverCreateInElection() {
		final Object testingCreateInElectionData[][] = {

			// Casos positivos
			{
				"citizen1", "election1", "Texto", "", null
			},
			// Casos negativos
			{
				"citizen1", "electionTest", "Quiero decir...", "http://www.google.es/", NumberFormatException.class
			/*
			 * No se puede crear un comentario para una elección que no existe.
			 */
			}, {
				"government", "election1", "", "", IllegalArgumentException.class
			/*
			 * El campo texto no puede ser blanco.
			 */
			}
		};

		for (int i = 0; i < testingCreateInElectionData.length; i++)
			this.templateCreateInElection((String) testingCreateInElectionData[i][0], (String) testingCreateInElectionData[i][1], (String) testingCreateInElectionData[i][2], (String) testingCreateInElectionData[i][3],
				(Class<?>) testingCreateInElectionData[i][4]);

	}

	protected void templateCreateInElection(final String authenticate, final String electionBean, final String text, final String picture, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int electionId = super.getEntityId(electionBean);
			final Election election = this.electionService.findOne(electionId);
			super.authenticate(authenticate);
			Assert.isTrue(election.getCelebrationDate().before(new Date()));
			final Comment comment = this.commentService.create(electionId, null);
			comment.setText(text);
			comment.setPicture(picture);
			final Comment saved = this.commentService.save(comment);
			Assert.isTrue(election.getComments().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.g: Comentar en cualquier candidatura.
	 */

	@Test
	public void driverCreateInCandidature() {
		final Object testingCreateInCandidatureData[][] = {

			// Casos positivos
			{
				"government", "candidature1", "Texto", "", null
			},
			// Casos negativos
			{
				"citizen1", "candidatureTest", "Quiero decir...", "http://www.google.es/", NumberFormatException.class
			/*
			 * No se puede crear un comentario para una candidatura que no existe.
			 */
			}, {
				"government", "candidature1", "", "", IllegalArgumentException.class
			/*
			 * El campo texto no puede ser blanco.
			 */
			}
		};

		for (int i = 0; i < testingCreateInCandidatureData.length; i++)
			this.templateCreateInCandidature((String) testingCreateInCandidatureData[i][0], (String) testingCreateInCandidatureData[i][1], (String) testingCreateInCandidatureData[i][2], (String) testingCreateInCandidatureData[i][3],
				(Class<?>) testingCreateInCandidatureData[i][4]);

	}

	protected void templateCreateInCandidature(final String authenticate, final String candidatureBean, final String text, final String picture, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int candidatureId = super.getEntityId(candidatureBean);
			final Candidature candidature = this.candidatureService.findOne(candidatureId);
			super.authenticate(authenticate);
			final Comment comment = this.commentService.create(candidatureId, null);
			comment.setText(text);
			comment.setPicture(picture);
			final Comment saved = this.commentService.save(comment);
			Assert.isTrue(candidature.getComments().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 15.h: Borrar un comentario que considere inapropiado. Cuando se borra un comentario también se borran todas sus respuestas.
	 */

	@Test
	public void driverDelete() {
		final Object testingDeleteData[][] = {

			// Casos positivos
			{
				"government", "comment1", null
			},
			// Casos negativos
			{
				"citizen1", null, NullPointerException.class
			/*
			 * Solo los agentes gubernamentales pueden eliminar comentarios
			 */
			}, {
				null, "comment1", IllegalArgumentException.class
			/*
			 * Los usuarios anonimos no pueden eliminar comentarios.
			 */
			}
		};

		for (int i = 0; i < testingDeleteData.length; i++)
			this.templateDelete((String) testingDeleteData[i][0], (String) testingDeleteData[i][1], (Class<?>) testingDeleteData[i][2]);

	}

	protected void templateDelete(final String authenticate, final String commentBean, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int commentId = super.getEntityId(commentBean);
			final Comment comment = this.commentService.findOne(commentId);
			super.authenticate(authenticate);
			this.commentService.delete(comment);
			Assert.isTrue(!this.commentService.findAll().contains(comment) && !comment.getActor().getComments().contains(comment));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}
}
