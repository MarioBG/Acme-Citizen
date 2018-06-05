package services;

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

	@Test
	public void driverLottery() {

		Object testingData[][] = { { "government", null }, { null, IllegalArgumentException.class },
				{ "bank1", IllegalArgumentException.class }, { "citizen1", IllegalArgumentException.class } };

		for (int i = 0; i < testingData.length; i++) {
			template((String) testingData[i][0], (Class<?>) testingData[i][1]);
		}

	}

	protected void template(String username, Class<?> expected) {
		Class<?> caught;

		caught = null;

		try {
			authenticate(username);
			Lottery lottery = lotteryService.create();
			lottery.setCelebrationDate(new Date());
			lottery.setLotteryName("loteria nacional");
			lottery.setPercentageForPrizes(2.2);
			lottery.setTicketCost(3.2);
			unauthenticate();
		} catch (Exception e) {
			caught = e.getClass();
		}

		checkExceptions(expected, caught);
	}

}
