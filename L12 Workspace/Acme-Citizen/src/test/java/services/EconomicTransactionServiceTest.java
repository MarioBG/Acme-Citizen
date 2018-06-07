package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.BankAccount;
import domain.EconomicTransaction;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class EconomicTransactionServiceTest extends AbstractTest {

	@Autowired
	private EconomicTransactionService economicTransactionService;

	@Autowired
	private BankAccountService bankAccountService;

	// Test ------------------------------------------

	/**
	 * Crear dinero, enviar dinero 14.i 15.n 15.r 16.b 16.d
	 */

	@Test(expected = NullPointerException.class)
	public void driver() {
		final Object testingCreate[][] = {
				// Casos positivos
				{ "citizen1", 123, 124, 10.0, false, null }, // Hacer una transaccion economica
				{ "government", 123, 124, 10.0, true, null }, // Crear dinero
				// Casos negativos
				{ "government1", 123, null, 10.0, false, NullPointerException.class }, // Debe haber
																						// cuenta de
																						// banco a la
																						// que se envia
				{ "bank1", null, 124, 10.0, false, NullPointerException.class }, // Tiene que tener
																					// cuenta bancaria
																					// para enviar
																					// dinero
		};

		for (int i = 0; i < testingCreate.length; i++)
			this.templateCreateAndEdit((String) testingCreate[i][0], (int) testingCreate[i][1],
					(int) testingCreate[i][2], (Double) testingCreate[i][3], (Boolean) testingCreate[i][4],
					(Class<?>) testingCreate[i][5]);
	}

	private void templateCreateAndEdit(final String username, final int creditor, final int debtor,
			final Double quantity, final Boolean doMoney, final Class<?> expected) {
		Class<?> caught;
		caught = null;

		EconomicTransaction economicTransaction;

		try {
			BankAccount bank1 = this.bankAccountService.findOne(creditor);
			BankAccount bankDebtor = this.bankAccountService.findOne(debtor);
			super.authenticate(username);
			economicTransaction = this.economicTransactionService.create();
			economicTransaction.setCreditor(bank1);
			economicTransaction.setDebtor(bankDebtor);
			economicTransaction.setQuantity(quantity);
			economicTransaction.setDoMoney(doMoney);

		} catch (final Throwable oops) {
			caught = oops.getClass();
		}
		this.checkExceptions(expected, caught);

	}

}
