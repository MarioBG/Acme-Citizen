
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Folder;
import forms.FolderForm;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class FolderServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private FolderService	folderService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 13.2: Manage his or her message folders, except for the
	 * system folders.
	 */

	@Test
	public void driverCreate() {
		final Object testingCreateData[][] = {

			// Casos positivos

			{
				"citizen1", "folder test", "folder11", null
			},

			// Casos negativos
			{
				"user1", "Folder test", "folder6", IllegalArgumentException.class
			},
			/**
			 * No se puede crear una carpeta de una carpeta padre que no es
			 * suya
			 */

			{
				null, "New folder", "folder1", NullPointerException.class
			},
			/**
			 * No se puede seleccionar una carpeta padre que no pertenece al
			 * usuario
			 */
			{
				"agent2", "", "folder41", NumberFormatException.class
			}
		/**
		 * El campo nombre no puede ser vacio
		 */
		};

		for (int i = 0; i < testingCreateData.length; i++)
			this.templateCreate((String) testingCreateData[i][0], (String) testingCreateData[i][1], (String) testingCreateData[i][2], (Class<?>) testingCreateData[i][3]);

	}

	protected void templateCreate(final String authenticate, final String name, final String parentBeanName, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int parentId = this.getEntityId(parentBeanName);
			super.authenticate(authenticate);
			final Folder folder = this.folderService.create(false, this.folderService.findOne(parentId));
			final FolderForm folderForm = this.folderService.construct(folder);
			folderForm.setName(name);
			folderForm.setParentId(parentId);
			final Folder folder2 = this.folderService.reconstruct(folderForm, null);
			final Folder folderSaved = this.folderService.save(folder2);
			Assert.isTrue(folderSaved.getParent().getChildren().contains(folderSaved));
			this.folderService.delete(folderSaved);
			Assert.isTrue(!this.folderService.findAll().contains(folderSaved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
