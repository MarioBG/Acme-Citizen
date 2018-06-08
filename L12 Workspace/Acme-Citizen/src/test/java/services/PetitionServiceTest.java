
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
import domain.GovernmentAgent;
import domain.Petition;

@ContextConfiguration(locations = {
	"classpath:spring/junit.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class PetitionServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private PetitionService			petitionService;

	@Autowired
	private CitizenService			citizenService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Tests ------------------------------------------------------------------

	/*
	 * Caso de uso 12.e: Listar las peticiones del sistema y navegar a los perfiles de los ciudadanos que
	 * las crearon, así como de los agentes gubernamentales a los que se dirigen.
	 */

	@Test
	public void driverListAndDisplayCitizenAndGovernmentAgent() {
		final Object testingListAndDisplayCitizenAndGovernmentAgentData[][] = {

			// Casos positivos
			{
				null, null
			}
		};

		for (int i = 0; i < testingListAndDisplayCitizenAndGovernmentAgentData.length; i++)
			this.templateListAndDisplayCitizenAndGovernmentAgent((String) testingListAndDisplayCitizenAndGovernmentAgentData[i][0], (Class<?>) testingListAndDisplayCitizenAndGovernmentAgentData[i][1]);

	}

	protected void templateListAndDisplayCitizenAndGovernmentAgent(final String authenticate, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			final Collection<Petition> petitions = this.petitionService.findNonDeleted();
			if (!petitions.isEmpty()) {
				final Petition petition = this.petitionService.findOne(petitions.iterator().next().getId());
				this.citizenService.findOne(petition.getCitizen().getId());
				if (!petition.getGovernmentAgents().isEmpty())
					this.governmentAgentService.findOne(petition.getGovernmentAgents().iterator().next().getId());
			}
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.d: Listar las peticiones que haya creado.
	 */

	@Test
	public void driverListYours() {
		final Object testingListYoursData[][] = {

			// Casos positivos
			{
				"citizen2", null
			},
			// Casos negativos
			{
				"government", IllegalArgumentException.class
			/*
			 * Sólo los ciudadanos pueden acceder a sus peticiones
			 */
			}
		};

		for (int i = 0; i < testingListYoursData.length; i++)
			this.templateListYours((String) testingListYoursData[i][0], (Class<?>) testingListYoursData[i][1]);

	}

	protected void templateListYours(final String authenticate, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			super.authenticate(authenticate);
			final Collection<Petition> petitions = this.petitionService.findByPrincipal();
			if (!petitions.isEmpty())
				for (final Petition petition : petitions)
					Assert.isTrue(petition.getCitizen().equals(this.citizenService.findByPrincipal()));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.b: Crear una petición. Mientras que no esté guardada en modo final, puede ser
	 * editada y se le pueden añadir agentes gubernamentales.
	 */

	@Test
	public void driverCreateAndEdit() {
		final Object testingCreateAndEditData[][] = {

			// Casos positivos
			{
				"citizen1", "Petición", "Quiero una cosa", "https://www.google.es/", false, "government", "government", null
			}, {
				"citizen2", "Hola", "¿Que haces?", "", false, "tvh", "tvh", null
			}, {
				"citizen1", "Otra que va", "Quiero otra cosa", "", false, "tvh", "tvh", null
			}, {
				"citizen2", "Futbol", "Más partidos de futbol", "https://www.lfp.es/", false, "government", "government", null
			}, {
				"citizen3", "Libertad", "Necesito esto", "", false, "government", "government", null
			},
			// Casos negativos
			{
				null, "Petición", "Quiero una cosa", "https://www.google.es/", false, "government", "government", IllegalArgumentException.class
			/*
			 * Un usuario anonimo no puede realizar peticiones
			 */
			}, {
				"citizen2", "", "¿Que haces?", "", false, "tvh", "tvh", IllegalArgumentException.class
			/*
			 * No se puede dejar el nombre de la petición en blanco
			 */
			}, {
				"citizen1", "Otra que va", "Quiero otra cosa", "", true, "tvh", "tvh", IllegalArgumentException.class
			/*
			 * No se puede añadir o eliminar agentes gubernamentales si la petición es final
			 */
			}, {
				"government", "Futbol", "Más partidos de futbol", "https://www.lfp.es/", false, "government", "government", IllegalArgumentException.class
			/*
			 * Únicamente los ciudadanos puede realizar peticiones
			 */
			}, {
				"citizen3", "Libertad", "Necesito esto", "", false, "government", "tvh", IllegalArgumentException.class
			/*
			 * No se puede eliminar a un agente gubernamental si previamente este no esta relacionado con la petición
			 */
			},
		};

		for (int i = 0; i < testingCreateAndEditData.length; i++)
			this.templateCreateAndEdit((String) testingCreateAndEditData[i][0], (String) testingCreateAndEditData[i][1], (String) testingCreateAndEditData[i][2], (String) testingCreateAndEditData[i][3], (boolean) testingCreateAndEditData[i][4],
				(String) testingCreateAndEditData[i][5], (String) testingCreateAndEditData[i][6], (Class<?>) testingCreateAndEditData[i][7]);

	}

	protected void templateCreateAndEdit(final String authenticate, final String name, final String description, final String picture, final boolean finalVersion, final String governmentAgentAddBean, final String governmentAgentRemoveBean,
		final Class<?> expected) {

		Class<?> caught;
		caught = null;
		Petition saved = null;

		try {
			final int governmentAgentAddId = super.getEntityId(governmentAgentAddBean);
			final int governmentAgentRemoveId = super.getEntityId(governmentAgentRemoveBean);
			final GovernmentAgent governmentAgentAdd = this.governmentAgentService.findOne(governmentAgentAddId);
			final GovernmentAgent governmentAgentRemove = this.governmentAgentService.findOne(governmentAgentRemoveId);
			super.authenticate(authenticate);
			final Petition petition = this.petitionService.create();
			petition.setName(name);
			petition.setDescription(description);
			petition.setPicture(picture);
			petition.setFinalVersion(finalVersion);
			saved = this.petitionService.save(petition);
			this.petitionService.addGovernmentAgent(saved.getId(), governmentAgentAddId);
			Assert.isTrue(saved.getGovernmentAgents().contains(governmentAgentAdd) && governmentAgentAdd.getPetitions().contains(saved));
			this.petitionService.removeGovernmentAgent(saved.getId(), governmentAgentRemoveId);
			Assert.isTrue(!saved.getGovernmentAgents().contains(governmentAgentRemove) && !governmentAgentRemove.getPetitions().contains(saved));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 14.c: Actualizar y borrar las peticiones que haya creado. El borrado es virtual (la
	 * información no se borra de la base de datos, pero la petición ya no se puede
	 * actualizar). Las peticiones borradas no se muestran en los listados.
	 */

	@Test
	public void driverEditAndDelete() {
		final Object testingEditAndDeleteData[][] = {

			// Casos positivos
			{
				"citizen2", "petition1", "Hola", "¿Que haces?", "", false, null
			},
			// Casos negativos
			{
				"citizen2", "petition1", "", "Quiero una cosa", "https://www.google.es/", false, IllegalArgumentException.class
			/*
			 * El nombre de la petición no puede ser blanco
			 */
			}, {
				"citizen2", "petition1", "Hola", "¿Que haces?", "http://www.google.es/", true, IllegalArgumentException.class
			/*
			 * No se puede eliminar virtualmente una petición si su estado es final.
			 */
			}
		};

		for (int i = 0; i < testingEditAndDeleteData.length; i++)
			this.templateEditAndDelete((String) testingEditAndDeleteData[i][0], (String) testingEditAndDeleteData[i][1], (String) testingEditAndDeleteData[i][2], (String) testingEditAndDeleteData[i][3], (String) testingEditAndDeleteData[i][4],
				(boolean) testingEditAndDeleteData[i][5], (Class<?>) testingEditAndDeleteData[i][6]);

	}

	protected void templateEditAndDelete(final String authenticate, final String petitionBean, final String name, final String description, final String picture, final boolean finalVersion, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int petitionId = super.getEntityId(petitionBean);
			final Petition petition = this.petitionService.findOne(petitionId);
			super.authenticate(authenticate);
			petition.setName(name);
			petition.setDescription(description);
			petition.setPicture(picture);
			petition.setFinalVersion(finalVersion);
			this.petitionService.save(petition);
			this.petitionService.flush();
			this.petitionService.virtualDelete(petition);
			Assert.isTrue(petition.getDeleted() == true && petition.getFinalVersion() == false);
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

	/*
	 * Caso de uso 15.g: Borrar una petición que considere inapropiada.
	 */

	@Test
	public void driverDelete() {
		final Object testingDeleteData[][] = {

			// Casos positivos
			{
				"government", "petition1", null
			},
			// Casos negativos
			{
				"citizen1", null, NullPointerException.class
			/*
			 * Solo los agentes gubernamentales pueden eliminar peticiones
			 */
			}, {
				null, "petition1", IllegalArgumentException.class
			/*
			 * Los usuarios anonimos no pueden eliminar peticiones.
			 */
			}
		};

		for (int i = 0; i < testingDeleteData.length; i++)
			this.templateDelete((String) testingDeleteData[i][0], (String) testingDeleteData[i][1], (Class<?>) testingDeleteData[i][2]);

	}

	protected void templateDelete(final String authenticate, final String petitionBean, final Class<?> expected) {

		Class<?> caught;
		caught = null;

		try {
			final int petitionId = super.getEntityId(petitionBean);
			final Petition petition = this.petitionService.findOne(petitionId);
			super.authenticate(authenticate);
			this.petitionService.delete(petition);
			Assert.isTrue(!this.petitionService.findAll().contains(petition) && !petition.getCitizen().getPetitions().contains(petition));
			super.unauthenticate();
		} catch (final Throwable oops) {
			caught = oops.getClass();
		}

		this.checkExceptions(expected, caught);
	}

}
