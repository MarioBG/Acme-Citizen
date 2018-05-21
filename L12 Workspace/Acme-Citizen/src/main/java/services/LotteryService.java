
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.LotteryRepository;
import domain.Actor;
import domain.Lottery;
import domain.LotteryTicket;

@Service
@Transactional
public class LotteryService {

	// Managed repository

	@Autowired
	private LotteryRepository	lotteryRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;


	// Constructors

	public LotteryService() {
		super();
	}

	// Simple CRUD methods

	public Lottery create() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final Lottery result = new Lottery();

		final Collection<LotteryTicket> lotterys = new ArrayList<LotteryTicket>();
		result.setLotteryTickets(lotterys);

		return result;
	}

	public Lottery save(final Lottery lottery) {

		Lottery result;

		result = this.lotteryRepository.save(lottery);
		return result;
	}

	public Collection<Lottery> findAll() {

		final Collection<Lottery> result = this.lotteryRepository.findAll();
		return result;
	}

	public Lottery findOne(final int lotteryId) {

		Assert.isTrue(lotteryId != 0);

		final Lottery result = this.lotteryRepository.findOne(lotteryId);
		return result;
	}

}
