package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Message;
import domain.Priority;
import forms.MessageForm;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class MessageServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private MessageService messageService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 13.1: Exchange messages with other actors and manage them, which
	 * includes deleting and moving them from one folder to another folder.
	 */

	@Test
	public void driverCreateMessage() {
		final Object testingCreateMessageData[][] = {

				// Casos positivos
				{ "bank1", "tvhisperia", "Subject test", "Body test", Priority.HIGH, "folder10", null },
				{ "bank1", "citizen2", "Petition", "I request something", Priority.NEUTRAL, "folder40", null },
				{ "government", "citizen1", "Alert!", "You posted a taboo word.", Priority.HIGH, "folder5", null },
				{ "citizen1", "citizen3", "Hola", "Que tal todo?", Priority.LOW, "folder30", null },
				{ "citizen2", "bank1", "Opinión", "¿Cómo fué?", Priority.NEUTRAL, "folder45", null },

				// Casos negativos
				{ "user1", null, "Subject test", "Body test", Priority.HIGH, "folder10",
						IllegalArgumentException.class },
				/*
				 * // * Se debe de seleccionar un destinatario //
				 */

				{ "agent1", "user2", "", "Dejé el mensaje sin asunto", Priority.NEUTRAL, "folder40",
						IllegalArgumentException.class },
				/*
				 * // * No se puede dejar en blanco el campo asunto //
				 */

				{ "admin", "customer1", "Alert!", "", Priority.HIGH, "folder5", IllegalArgumentException.class },
				/*
				 * // * No se puede dejar en blanco el campo cuerpo //
				 */

				{ null, "agent1", "Opinión", "¿Cómo fué?", Priority.NEUTRAL, "folder45", NullPointerException.class },
				/*
				 * Solo los usuarios autenticados pueden enviar mensajes
				 */
		};

		for (int i = 0; i < testingCreateMessageData.length; i++)
			this.templateCreateMessage((String) testingCreateMessageData[i][0], (String) testingCreateMessageData[i][1],
					(String) testingCreateMessageData[i][2], (String) testingCreateMessageData[i][3],
					(Priority) testingCreateMessageData[i][4], (String) testingCreateMessageData[i][5],
					(Class<?>) testingCreateMessageData[i][6]);

	}

	protected void templateCreateMessage(String authenticate, String recipientBeanName, String subject, String body,
			Priority priority, String folderBeanName, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Message message = messageService.create();
			MessageForm messageForm = messageService.construct(message);
			messageForm.setSubject(subject);
			messageForm.setBody(body);
			messageForm.setPriority(priority);

			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.1: Broadcast a message to the actors of the system.
	 */

	@Test
	public void driverCreateNotification() {
		final Object testingCreateNotificationData[][] = {

				// Casos positivos
				{ "government", "Subject test", "Body test", Priority.HIGH, null },
				// Casos negativos
				{ "", "Alert!", "This is a notification", Priority.HIGH,
						IllegalArgumentException.class }, /*
															 * Sólo el administrador puede enviar notificaciones
															 */

		};

		for (int i = 0; i < testingCreateNotificationData.length; i++)
			this.templateCreateNotification((String) testingCreateNotificationData[i][0],
					(String) testingCreateNotificationData[i][1], (String) testingCreateNotificationData[i][2],
					(Priority) testingCreateNotificationData[i][3], (Class<?>) testingCreateNotificationData[i][4]);

	}

	protected void templateCreateNotification(String authenticate, String subject, String body, Priority priority,
			Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			Message message = messageService.create();
			MessageForm messageForm = messageService.construct(message);
			messageForm.setSubject(subject);
			messageForm.setBody(body);
			messageForm.setPriority(priority);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
