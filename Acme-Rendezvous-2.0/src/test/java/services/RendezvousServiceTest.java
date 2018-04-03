package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.GpsCoordinate;
import domain.Petition;
import domain.Citizen;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@Transactional
public class RendezvousServiceTest extends AbstractTest {

	// System under test ------------------------------------------------------

	@Autowired
	private RendezvousService rendezvousService;

	@Autowired
	private UserService userService;

	@PersistenceContext
	private EntityManager entityManager;

	// Tests ------------------------------------------------------------------

	// 5.2 Acme-Rendezvous Create a rendezvous, which heâs implicitly assumed
	// to attend.
	@Test
	public void driverCreate() {
		GpsCoordinate gpsCoordinate = new GpsCoordinate();
		gpsCoordinate = createGpsOk();
		GpsCoordinate gpsLatitudeNull = createGpsLatitudeNull();
		GpsCoordinate gpsLongitudeNull = createGpsLongitudeNull();
		GpsCoordinate gpsLatitudeOutRangeM = createGpsLatitudeOutRangeMore();
		GpsCoordinate gpsLongitudeOutRangeM = createGpsLongitudeOutRangeMore();
		GpsCoordinate gpsLatitudeOutRangeL = createGpsLatitudeOutRangeLess();
		GpsCoordinate gpsLongitudeOutRangeL = createGpsLongitudeOutRangeLess();

		Object testingData[][] = {
				// positive test
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsCoordinate, false, false, null },
				// negative test: usuario no valido
				{
						null,
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsCoordinate, false, false,
						IllegalArgumentException.class },
				// latitude null
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLatitudeNull, false, false, null },
				// longitude null
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLongitudeNull, false, false,
						null },
				// latitude out of range up
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLatitudeOutRangeM, false, false,
						javax.validation.ConstraintViolationException.class },
				// longitude out of range up
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLongitudeOutRangeM, false,
						false,
						javax.validation.ConstraintViolationException.class },
				// latitude out of range down
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLatitudeOutRangeL, false, false,
						javax.validation.ConstraintViolationException.class },
				// longitude out of range down
				{
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsLongitudeOutRangeL, false,
						false,
						javax.validation.ConstraintViolationException.class }, };

		for (int i = 0; i < testingData.length; i++) {
			templateCreate((String) testingData[i][0],
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(GpsCoordinate) testingData[i][5],
					(boolean) testingData[i][6], (boolean) testingData[i][7],
					(Class<?>) testingData[i][8]);
		}

	}

	private void templateCreate(String user, String name, String description,
			String picture, String date, GpsCoordinate gps, boolean adult,
			boolean finalVersion, Class<?> expected) {
		Petition rendezvous;
		Class<?> caught;
		caught = null;
		Date fecha = new Date();

		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			fecha = pattern.parse(date);
		} catch (ParseException e) {
			e.getClass();
		}

		try {
			authenticate(user);
			rendezvous = this.rendezvousService.create();
			rendezvous.setName(name);
			rendezvous.setDescription(description);
			rendezvous.setPicture(picture);
			rendezvous.setMoment(fecha);
			rendezvous.setGpsCoordinate(gps);
			rendezvous.setAdult(adult);
			rendezvous.setFinalVersion(finalVersion);

			this.rendezvousService.save(rendezvous);
			this.rendezvousService.flush();
			unauthenticate();
		} catch (Throwable oops) {

			caught = oops.getClass();
		}
		checkExceptions(expected, caught);

	}

	// 5.2 Acme-Rendezvous Create a rendezvous, Note that a user may edit his or
	// her rendezvouses as long as they arenât saved them in final mode.
	@Test
	public void driverEdit() {
		GpsCoordinate gpsCoordinate = new GpsCoordinate();
		gpsCoordinate = createGpsOk();

		Object testingData[][] = {
				// positive test
				{
						"rendezvous1",
						"user1",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2020 19:32", gpsCoordinate, false, false, null },
				// negative test: usuario no válido
				{
						"rendezvous1",
						"user2",
						"name",
						"description",
						"http://www.uwyo.edu/reslife-dining/_files/re-design-images/dining-logos/rendezvouslogo_2016.png",
						"10/05/2000", gpsCoordinate, false, true,
						java.lang.IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++) {
			templateEdit(getEntityId((String) testingData[i][0]),
					(String) testingData[i][1], (String) testingData[i][2],
					(String) testingData[i][3], (String) testingData[i][4],
					(String) testingData[i][5],
					(GpsCoordinate) testingData[i][6],
					(boolean) testingData[i][7], (boolean) testingData[i][8],
					(Class<?>) testingData[i][9]);
		}

	}

	private void templateEdit(int rendezvousId, String user, String name,
			String description, String picture, String date, GpsCoordinate gps,
			boolean adult, boolean finalVersion, Class<?> expected) {
		Petition rendezvous;
		rendezvous = new Petition();
		Class<?> caught;
		caught = null;
		Date fecha = new Date();

		SimpleDateFormat pattern = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		try {
			fecha = pattern.parse(date);
		} catch (ParseException e) {
			e.getClass();
		}

		try {
			authenticate(user);
			rendezvous = this.rendezvousService.findOne(rendezvousId);

			rendezvous.setName(name);
			rendezvous.setDescription(description);
			rendezvous.setPicture(picture);
			rendezvous.setMoment(fecha);
			rendezvous.setGpsCoordinate(gps);
			rendezvous.setAdult(adult);
			rendezvous.setFinalVersion(finalVersion);

			this.rendezvousService.save(rendezvous);
			this.rendezvousService.flush();
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}

	// Test delete de user
	// 5.3 Acme Rendezvous Update or delete the rendezvouses that he or she’s
	// created. Deletion is virtual, that is: the information is not removed
	// from the database, but the rendezvous cannot be updated. Deleted
	// rendezvouses are flagged as such when they are displayed.

	@Test
	public void driverDeleteUser() {

		Object testingData[][] = {
		// positive test
		{ "user2", "rendezvous2", null },

		};
		for (int i = 0; i < testingData.length; i++) {
			templateDeleteUser((String) testingData[i][0],
					getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
		}

	}

	private void templateDeleteUser(String user, int rendezvousId,
			Class<?> expected) {
		Petition rendezvous;
		rendezvous = new Petition();
		Class<?> caught;
		caught = null;

		try {
			authenticate(user);
			rendezvous = this.rendezvousService.findOne(rendezvousId);

			this.rendezvousService.FakeDelete(rendezvous);
			this.rendezvousService.flush();
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}
		checkExceptions(expected, caught);
	}

	// Test Assistant
	// 5.4 Acem-Rendezvous RSVP a rendezvous or cancel it. “RSVP” is a French
	// term that means “Réservez, s’il vous plaît”; it’s commonly used in the
	// anglo-saxon world to mean “I will attend this rendezvous”; it’s
	// pronounced as “/ri:’serv/”, “/ri:’serv’silvu’ple/”, or “ɑːresviːˈpiː”.
	// When a user RSVPs a rendezvous, he or she is assumed to attend it.

	@Test
	public void driverAssistUser() {

		Object testingData[][] = {
				// positive test user 1 asiste a rendezvous 1
				{ "user1", "rendezvous1", null },
				// user3 menor de edad asiste a rendezvous para mayores
				{ "user3", "rendezvous2", IllegalArgumentException.class },
				// user2 mayor de edad asiste a rendezvous para mayores
				{ "user2", "rendezvous2", null },

		};

		for (int i = 0; i < testingData.length; i++) {
			templateAssistUser((String) testingData[i][0],
					getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
		}

	}

	private void templateAssistUser(String user, int rendezvousId,
			Class<?> expected) {
		Petition rendezvous;
		Citizen userF;
		Class<?> caught;

		caught = null;

		try {
			authenticate(user);
			userF = this.userService.findByPrincipal();
			this.rendezvousService.YesRSVP(rendezvousId);
			this.rendezvousService.flush();

			rendezvous = this.rendezvousService.findOne(rendezvousId);
			userF = this.userService.findByPrincipal();
			Assert.isTrue(rendezvous.getAttendants().contains(userF));
			this.rendezvousService.flush();
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}
		checkExceptions(expected, caught);
	}

	// Test not Assistant
	// 5.4 Acem-Rendezvous RSVP a rendezvous or cancel it. “RSVP” is a French
	// term that means “Réservez, s’il vous plaît”; it’s commonly used in the
	// anglo-saxon world to mean “I will attend this rendezvous”; it’s
	// pronounced as “/ri:’serv/”, “/ri:’serv’silvu’ple/”, or “ɑːresviːˈpiː”.
	// When a user RSVPs a rendezvous, he or she is assumed to attend it.

	@Test
	public void driverNotAssistUser() {

		Object testingData[][] = {


				// positive test
				{ "user1", "rendezvous1", null},
				// negative test
				{ "user1", "rendezvous2", IllegalArgumentException.class },
				// Manager no puede asistir a un rendezvous
				{ "manager1", "rendezvous1", IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++) {
			templateNotAssistUser((String) testingData[i][0],
					getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
		}

	}

	private void templateNotAssistUser(String user, int rendezvousId,
			Class<?> expected) {
		Petition rendezvous;
		Citizen userF;
		Class<?> caught;

		caught = null;

		try {
			authenticate(user);

			this.rendezvousService.NoRSVP(rendezvousId);
			this.rendezvousService.flush();

			rendezvous = this.rendezvousService.findOne(rendezvousId);
			userF = this.userService.findByPrincipal();
			Assert.isTrue(!rendezvous.getAttendants().contains(userF));
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
			this.entityManager.clear();
		}
		checkExceptions(expected, caught);
	}

	// 5.5 Acme Rendezvous List the rendezvouses that he or she’s RSVPd.
	@Test
	public void driverListAssistUser() {

		Object testingData[][] = {


				// positive test
				{ "user1", "rendezvous1", null},
				// negative test, comprueba que un user efectivamente no tenga un rendezvous al que no va a asistir en su lista de attendants
				{ "user1", "rendezvous2", IllegalArgumentException.class },

		};
		for (int i = 0; i < testingData.length; i++) {
			templateListAssistUser((String) testingData[i][0],
					getEntityId((String) testingData[i][1]),
					(Class<?>) testingData[i][2]);
		}

	}

	private void templateListAssistUser(String user, int rendezvousId,
			Class<?> expected) {
		Petition rendezvous;
		Collection<Petition> rendezvousAssists = new ArrayList<Petition>();
		Citizen principalUser;
		Class<?> caught;

		caught = null;

		try {
			authenticate(user);
			principalUser = this.userService.findByPrincipal();
			rendezvous = this.rendezvousService.findOne(rendezvousId);
			rendezvousAssists = this.rendezvousService.findByAttendantId(principalUser.getId());
			Assert.isTrue(rendezvousAssists.contains(rendezvous));
			unauthenticate();

		} catch (Throwable oops) {
			caught = oops.getClass();
		}
		checkExceptions(expected, caught);
	}
	

	// Creación de gps

	private GpsCoordinate createGpsOk() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(89.0);
		res.setLongitude(179.0);

		return res;
	}

	private GpsCoordinate createGpsLatitudeNull() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(null);
		res.setLongitude(179.0);

		return res;
	}

	private GpsCoordinate createGpsLongitudeNull() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(89.0);
		res.setLongitude(null);

		return res;
	}

	private GpsCoordinate createGpsLatitudeOutRangeMore() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(100.0);
		res.setLongitude(179.0);

		return res;
	}

	private GpsCoordinate createGpsLongitudeOutRangeMore() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(89.0);
		res.setLongitude(2005585.0);

		return res;
	}

	private GpsCoordinate createGpsLatitudeOutRangeLess() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(-100.0);
		res.setLongitude(179.0);

		return res;
	}

	private GpsCoordinate createGpsLongitudeOutRangeLess() {
		GpsCoordinate res = new GpsCoordinate();

		res.setLatitude(89.0);
		res.setLongitude(-200.0);

		return res;
	}

}
