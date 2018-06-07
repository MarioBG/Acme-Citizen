package services;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import domain.Lottery;
import utilities.AbstractTest;

@ContextConfiguration(locations = { "classpath:spring/junit.xml" })
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class LotteryServiceTest extends AbstractTest {

	@Autowired
	private LotteryService lotteryService;

	/**
	 * Organizar loterias
	 */

	@Test
	public void driverLottery() {

		Object testingData[][] = { { "government", "12/12/2018", "loteria nacional", 1.1, 10.0, 0.0, null }, // Test
																												// positivo
				// Casos negativos
				{ null, "12/12/2018", "loteria nacional", 10.0, 10.0, 0.0, IllegalArgumentException.class }, // Se
																												// intenta
																												// crear
																												// una
																												// lotería
																												// sin
																												// usuario
																												// logeado
				{ "gov", "12/12/2018", "loteria 1", -1.1, 10.0, 2.0, IllegalArgumentException.class }, // No se puede
																										// crear una
																										// loteria con
																										// un porcentaje
																										// negativo
				{ "gov", "12/12/2018", null, 1.1, 10.0, 2.0, IllegalArgumentException.class }, // No se pude crear una
																								// loteria sin nombre

		};

		for (int i = 0; i < testingData.length; i++) {
			this.templateCreateAndEdit((String) testingData[i][0], (String) testingData[i][1],
					(String) testingData[i][2], (Double) testingData[i][3], (Double) testingData[i][4],
					(Double) testingData[i][5], (Class<?>) testingData[i][6]);
		}

	}

	private void templateCreateAndEdit(final String username, final String date, final String lotteryName,
			final Double percentage, final Double ticketCost, final Double quantity, final Class<?> expected) {
		Class<?> caught;

		caught = null;
		Lottery lottery;

		Date celebrationDate = null;
		try {
			celebrationDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			super.authenticate(username);
			lottery = lotteryService.create();
			lottery.setCelebrationDate(celebrationDate);
			lottery.setLotteryName(lotteryName);
			lottery.setPercentageForPrizes(percentage);
			lottery.setTicketCost(ticketCost);
			lottery.setQuantity(quantity);

		} catch (Exception e) {
			caught = e.getClass();
			this.lotteryService.flush();
		}

		this.checkExceptions(expected, caught);
	}

}
