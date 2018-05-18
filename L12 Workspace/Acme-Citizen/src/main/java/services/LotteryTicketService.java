package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
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

}
