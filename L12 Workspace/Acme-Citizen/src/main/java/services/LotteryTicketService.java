package services;

import java.util.Collection;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Citizen;
import domain.Lottery;
import domain.LotteryTicket;
import repositories.LotteryTicketRepository;

@Service
@Transactional
public class LotteryTicketService {

	// Managed repository

	@Autowired
	private LotteryTicketRepository lotteryTicketRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;
	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private CitizenService citizenService;

	// Constructors

	public LotteryTicketService() {
		super();
	}

	// Simple CRUD methods

	public LotteryTicket create(final int lotteryId) {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final LotteryTicket result = new LotteryTicket();

		Lottery lottery = this.lotteryService.findOne(lotteryId);

		result.setLottery(lottery);
		result.setCitizen((Citizen) principal);
		result.getLottery().getLotteryTickets().add(result);
		result.setNumber(asignNumber(lotteryId));

		return result;
	}

	public LotteryTicket save(final LotteryTicket lotteryTicket) {

		LotteryTicket result;
		result = this.lotteryTicketRepository.save(lotteryTicket);
		return result;
	}

	public Collection<LotteryTicket> findAll() {

		Collection<LotteryTicket> result = this.lotteryTicketRepository.findAll();
		return result;
	}

	public LotteryTicket findOne(final int lotteryTicketId) {

		Assert.isTrue(lotteryTicketId != 0);

		LotteryTicket result = this.lotteryTicketRepository.findOne(lotteryTicketId);
		return result;
	}

	public String asignNumber(int lotteryId) {

		Lottery lottery = lotteryService.findOne(lotteryId);
		Collection<LotteryTicket> tickets = lottery.getLotteryTickets();

		Random random = new Random();
		int randomIndex = 0;

		while (tickets.size() > 0 && tickets.size() < 1000000) {
			randomIndex = random.nextInt(tickets.size());
		}

		for (LotteryTicket ticket : tickets) {
			String number = Integer.toString(randomIndex);
			if (ticket.getNumber().equals(number)) {
				randomIndex = random.nextInt(tickets.size());
			}
		}

		return Integer.toString(randomIndex);
	}

	public void buyLottery(int lotteryId) {
		LotteryTicket ticket = create(lotteryId);
		Citizen citizen = citizenService.findByPrincipal();
		Assert.notNull(citizen);
		Assert.notNull(ticket);

		Double money = citizen.getBankAccount().getMoney();
		if (money >= ticket.getLottery().getTicketCost()) {
			Double newMoney = money - ticket.getLottery().getTicketCost();
			citizen.getBankAccount().setMoney(newMoney);
			citizen.getLotteryTickets().add(ticket);
			save(ticket);
			citizenService.save(citizen);
		}

	}

}
