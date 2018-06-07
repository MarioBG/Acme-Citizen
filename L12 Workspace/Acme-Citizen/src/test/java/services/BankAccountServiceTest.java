package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Actor;
import domain.BankAccount;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class BankAccountServiceTest extends AbstractTest {

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private ActorService actorService;

	// Test ------------------------------------------

	@Test(expected = NullPointerException.class)
	public void driver() {
		final Object testingCreate[][] = {
				// Casos positivos
				{ "bank1", 151, "98746544654487", 0.0, null },
				// Casos negativos
				{ "citizen1", 151, "98746544654487", 10.0, IllegalArgumentException.class }, // Un
																								// ciudadano
																								// no
																								// puede
																								// crear
																								// una cuenta del banco

				{ "bank1", null, "12345678912345", 0.0, NullPointerException.class }, // Debe haber
																						// actor al que crearle la
																						// cuenta
																						// del banco
				{ "bank1", 151, null, 0.0, NullPointerException.class }, // Tiene que tener el numero de cuenta para
																			// poder crear la cuenta
		};

		for (int i = 0; i < testingCreate.length; i++)
			this.templateCreateAndEdit((String) testingCreate[i][0], (int) testingCreate[i][1],
					(String) testingCreate[i][2], (Double) testingCreate[i][3], (Class<?>) testingCreate[i][4]);
	}

	private void templateCreateAndEdit(final String username, final int actorId, final String accountNumber,
			final Double money, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		BankAccount bankAccount;
		Actor actor = this.actorService.findOne(actorId);

		try {
			super.authenticate(username);
			bankAccount = this.bankAccountService.create();
			bankAccount.setActor(actor);
			bankAccount.setAccountNumber(accountNumber);
			bankAccount.setMoney(money);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
