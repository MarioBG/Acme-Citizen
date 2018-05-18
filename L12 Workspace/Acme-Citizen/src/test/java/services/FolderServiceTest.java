package services;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Folder;
import forms.FolderForm;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FolderServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private FolderService folderService;

	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 13.2: Manage his or her message folders, except for the
	 * system folders.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

				// Casos positivos
				{ "user1", "Folder test", "folder6", null },
				// Casos negativos
				{ "user2", "New folder", "folder1",
						IllegalArgumentException.class }, /*
														 * No se puede
														 * seleccionar una
														 * carpeta padre que no
														 * pertenece al usuario
														 */
				{ "agent2", "", "folder41", ConstraintViolationException.class } /*
																				 * El
																				 * campo
																				 * nombre
																				 * no
																				 * puede
																				 * ser
																				 * vacio
																				 */
		};

		for (int i = 0; i < testingCreateData.length; i++)
			this.templateCreate((String) testingCreateData[i][0],
					(String) testingCreateData[i][1],
					(String) testingCreateData[i][2],
					(Class<?>) testingCreateData[i][3]);

	}

	protected void templateCreate(String authenticate, String name,
			String parentBeanName, Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			int parentId = getEntityId(parentBeanName);
			super.authenticate(authenticate);
			Folder folder = folderService.create(false, null);
			FolderForm folderForm = folderService.construct(folder);
			folderForm.setName(name);
			folderForm.setParentId(parentId);
			Folder folder2 = folderService.reconstruct(folderForm, null);
			Folder folderSaved = folderService.save(folder2);
			Assert.isTrue(folderSaved.getParent().getChildren()
					.contains(folderSaved));
			folderService.delete(folderSaved);
			Assert.isTrue(!folderService.findAll().contains(folderSaved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
