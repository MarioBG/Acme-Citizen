package services;

import java.util.Collection;

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

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final LotteryTicket result = new LotteryTicket();

		final Lottery lottery = this.lotteryService.findOne(lotteryId);

		result.setLottery(lottery);
		result.setCitizen((Citizen) principal);
		result.setNumber(getNumberTicket(lotteryId));

		return result;
	}

	public LotteryTicket save(final LotteryTicket lotteryTicket) {

		LotteryTicket result;
		result = this.lotteryTicketRepository.save(lotteryTicket);
		return result;
	}

	public Collection<LotteryTicket> findAll() {

		final Collection<LotteryTicket> result = this.lotteryTicketRepository
				.findAll();
		return result;
	}

	public LotteryTicket findOne(final int lotteryTicketId) {

		Assert.isTrue(lotteryTicketId != 0);

		final LotteryTicket result = this.lotteryTicketRepository
				.findOne(lotteryTicketId);
		return result;
	}

	private String getNumberTicket(int lotteryId) {
		String numberTicket = asignNumberTicket();
		Lottery lottery = this.lotteryService.findOne(lotteryId);
		for (LotteryTicket ticket : lottery.getLotteryTickets()) {
			while (ticket.getNumber().equals(numberTicket)) {
				numberTicket = asignNumberTicket();
			}
		}
		return numberTicket;
	}

	private String asignNumberTicket() {

		Integer num1 = (int) (Math.random() * 1000000);
		String numberTicket = num1.toString();

		if (numberTicket.length() == 1) {
			numberTicket = "00000" + numberTicket;
		} else if (numberTicket.length() == 2) {
			numberTicket = "0000" + numberTicket;
		} else if (numberTicket.length() == 3) {
			numberTicket = "000" + numberTicket;
		} else if (numberTicket.length() == 4) {
			numberTicket = "00" + numberTicket;
		} else if (numberTicket.length() == 5) {
			numberTicket = "0" + numberTicket;
		}

		return numberTicket;

	}

	public void buyLottery(final int lotteryId) {
		final LotteryTicket ticket = this.create(lotteryId);
		final Citizen citizen = this.citizenService.findByPrincipal();
		final Lottery lottery = this.lotteryService.findOne(lotteryId);
		final double cost = ticket.getLottery().getTicketCost();
		Assert.notNull(citizen);
		Assert.notNull(ticket);
		Assert.notNull(citizen.getBankAccount(), "No hay cuenta de banco");

		final Double money = citizen.getBankAccount().getMoney();
		if (money >= cost) {
			Double newMoney = money - ticket.getLottery().getTicketCost();
			newMoney = (double) Math.round(newMoney * 100) / 100;
			citizen.getBankAccount().setMoney(newMoney);
			citizen.getLotteryTickets().add(ticket);
			lottery.getLotteryTickets().add(ticket);

			Double newQuantity = lottery.getQuantity() + cost;
			newQuantity = (double) Math.round(newQuantity * 100) / 100;
			lottery.setQuantity(newQuantity);

			// this.lotteryTicketRepository.flush();
			this.save(ticket);
			this.citizenService.save(citizen);
			this.lotteryService.save(lottery);
		}

	}

	public Collection<LotteryTicket> findAllByCitizenId(int id) {
		return this.lotteryTicketRepository.findAllByCitizenId(id);
	}

}
