package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import utilities.AbstractTest;
import domain.Comment;
import domain.Petition;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class CommentServiceTest extends AbstractTest {

	// Supporting services ------------------------------------------

	@Autowired
	CommentService commentService;

	@Autowired
	RendezvousService rendezvousService;

	// 5.6 Acme-Rendezvous Comment on the rendezvouses that he or she has RSVPd.
	@SuppressWarnings("unchecked")
	@Test
	public void driverCreateAndSaveComment() {

		final Collection<Comment> listarComment = this
				.createCommentsForTesting();
		final Iterator<Comment> iterador = listarComment.iterator();

		final Object testingData[][] = {

				{
						// usuario "user1" crea un comment para un rendezvous
						// "rendezvous1" al que asiste
						"test create1", "https://testPrueba.com", null,
						null, "rendezvous1", "user1", null },
				{
						// El usuario "user1" va a crear un commment para un
						// rendezvous "rendezvous2" al q no va a asistir
						"test create2", "https://testPrueba.com", null,
						null, "rendezvous2", "user1",
						IllegalArgumentException.class },
				{
						// el usuario "user1" va a responder a un comentario
						// escrito en una cita a la q asiste
						"test create3", "https://testPrueba.com", null,
						iterador.next(), "rendezvous1", "user1", null },
				{
						// el usuario "user3" va a responder a un comentario
						// escrito en una cita a la q no asiste
						"test create3", "https://testPrueba.com", null,
						iterador.next(), "rendezvous1", "user3",
						IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {

			this.templateCreateAndSave(
					(String) testingData[i][0], (String) testingData[i][1],
					(List<Comment>) testingData[i][2],
					(Comment) testingData[i][3],
					super.getEntityId((String) testingData[i][4]),
					(String) testingData[i][5], (Class<?>) testingData[i][6]);
		}

	}

	private void templateCreateAndSave(final String text,
			final String picture, final List<Comment> replies,
			final Comment commentParent, final int rendezvousId,
			final String userName, final Class<?> expected) {
		final Petition rendezvousComment;
		Comment comment;

		Class<?> caught = null;

		try {
			super.authenticate(userName);
			rendezvousComment = this.rendezvousService.findOne(rendezvousId);
			comment = this.commentService.create();
			comment.setText(text);
			comment.setPicture(picture);
			comment.setReplies(replies);
			comment.setCommentParent(commentParent);
			comment.setRendezvous(rendezvousComment);
			comment = this.commentService.save(comment);

			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);
	}

	private Collection<Comment> createCommentsForTesting() {

		final Collection<Comment> res = new ArrayList<Comment>();
		final Comment comment;
		final Comment commentDiferentRendezvous;

		comment = this.commentService.findOne(this.getEntityId("comment1"));
		commentDiferentRendezvous = this.commentService.findOne(this
				.getEntityId("comment2"));

		res.add(comment);
		res.add(commentDiferentRendezvous);

		return res;
	}

	// 6.1 Acme-Rendezvous Remove a comment that he or she thinks is inappropriate.

	@Test
	public void driverAdminDeleteComment() {

		final Object testingData[][] = {

		{
				// Admin "admin" borra un comentario "comment2" qye ya
				// existe
				"admin", "comment1", null, },

		{
				// El usuario "user1" va a intentar borrar un comentario
				"user1", "comment1", IllegalArgumentException.class },

		};

		for (int i = 0; i < testingData.length; i++) {

			this.templateDelete((String) testingData[i][0],
					(String) testingData[i][1], (Class<?>) testingData[i][2]);
		}

	}

	private void templateDelete(final String actor, final String comment,
			final Class<?> expected) {
		Comment comment1;

		Class<?> caught = null;

		try {
			this.authenticate(actor);
			comment1 = this.commentService.findOne(this.getEntityId(comment));
			this.commentService.delete(comment1);

			this.unauthenticate();
			this.commentService.flush();
		} catch (final Throwable oops) {
			caught = oops.getClass();

		}
		this.checkExceptions(expected, caught);
	}

}
