
package services;

import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LotteryTicketRepository;
import domain.Actor;
import domain.Citizen;
import domain.Lottery;
import domain.LotteryTicket;

@Service
@Transactional
public class LotteryTicketService {

	// Managed repository

	@Autowired
	private LotteryTicketRepository	lotteryTicketRepository;

	// Supporting services

	@Autowired
	private ActorService			actorService;
	@Autowired
	private LotteryService			lotteryService;
	@Autowired
	private CitizenService			citizenService;


	// Constructors

	public LotteryTicketService() {
		super();
	}

	// Simple CRUD methods

	public LotteryTicket create(final int lotteryId) {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final LotteryTicket result = new LotteryTicket();

		final Lottery lottery = this.lotteryService.findOne(lotteryId);

		result.setLottery(lottery);
		result.setCitizen((Citizen) principal);
		result.getLottery().getLotteryTickets().add(result);
		result.setNumber(this.asignNumber(lotteryId));

		return result;
	}

	public LotteryTicket save(final LotteryTicket lotteryTicket) {

		LotteryTicket result;
		result = this.lotteryTicketRepository.save(lotteryTicket);
		return result;
	}

	public Collection<LotteryTicket> findAll() {

		final Collection<LotteryTicket> result = this.lotteryTicketRepository.findAll();
		return result;
	}

	public LotteryTicket findOne(final int lotteryTicketId) {

		Assert.isTrue(lotteryTicketId != 0);

		final LotteryTicket result = this.lotteryTicketRepository.findOne(lotteryTicketId);
		return result;
	}

	public String asignNumber(final int lotteryId) {

		final Lottery lottery = this.lotteryService.findOne(lotteryId);
		final Collection<LotteryTicket> tickets = lottery.getLotteryTickets();

		final Random random = new Random();
		int randomIndex = 0;

		while (tickets.size() > 0 && tickets.size() < 1000000)
			randomIndex = random.nextInt(tickets.size());

		for (final LotteryTicket ticket : tickets) {
			final String number = Integer.toString(randomIndex);
			if (ticket.getNumber().equals(number))
				randomIndex = random.nextInt(tickets.size());
		}

		return Integer.toString(randomIndex);
	}

	public void buyLottery(final int lotteryId) {
		final LotteryTicket ticket = this.create(lotteryId);
		final Citizen citizen = this.citizenService.findByPrincipal();
		Assert.notNull(citizen);
		Assert.notNull(ticket);
		Assert.notNull(citizen.getBankAccount(), "No hay cuenta de banco");

		final Double money = citizen.getBankAccount().getMoney();
		if (money >= ticket.getLottery().getTicketCost()) {
			final Double newMoney = money - ticket.getLottery().getTicketCost();
			citizen.getBankAccount().setMoney(newMoney);
			citizen.getLotteryTickets().add(ticket);
			this.save(ticket);
			this.citizenService.save(citizen);
		}

	}

}
