package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.EconomicTransaction;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EconomicTransactionServiceTest extends AbstractTest {

	@Autowired
	private EconomicTransactionService economicTransactionService;

	// Test ------------------------------------------

	@Test()
	public void driver() {
		final Object testingCreate[][] = {
				// Casos positivos
				{ "citizen1", "98746544654402", "98746544654487", 10.0, false, null },
				// Casos negativos
				{ "citizen1", "98746544654402", "98746544654487", 10.0, true, IllegalArgumentException.class }, // Un
																												// ciudadano
																												// no
																												// puede
																												// crear
																												// dinero

				{ "government1", "98746544654402", null, 10.0, false, NullPointerException.class }, // Debe haber
																									// cuenta de
																									// banco a la
																									// que se envia
				{ "bank1", null, "98746544654487", 10.0, false, NullPointerException.class }, // Tiene que tener
																								// cuenta bancaria
																								// para enviar
																								// dinero
		};

		for (int i = 0; i < testingCreate.length; i++)
			this.templateCreateAndEdit((String) testingCreate[i][0], (String) testingCreate[i][1],
					(String) testingCreate[i][2], (Double) testingCreate[i][3], (Boolean) testingCreate[i][4],
					(Class<?>) testingCreate[i][5]);
	}

	private void templateCreateAndEdit(final String username, final String creditor, final String debtor,
			final Double quantity, final Boolean doMoney, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		EconomicTransaction economicTransaction;

		try {
			super.authenticate(username);
			economicTransaction = this.economicTransactionService.create();
			economicTransaction.getCreditor().setAccountNumber(creditor);
			economicTransaction.getDebtor().setAccountNumber(debtor);
			economicTransaction.setQuantity(quantity);
			economicTransaction.setDoMoney(doMoney);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
