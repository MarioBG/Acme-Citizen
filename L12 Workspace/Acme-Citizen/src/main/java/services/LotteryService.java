package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.Lottery;
import domain.LotteryTicket;
import repositories.LotteryRepository;

@Service
@Transactional
public class LotteryService {

	// Managed repository

	@Autowired
	private LotteryRepository lotteryRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	// Constructors

	public LotteryService() {
		super();
	}

	// Simple CRUD methods

	public Lottery create() {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final Lottery result = new Lottery();

		Collection<LotteryTicket> lotterys = new ArrayList<LotteryTicket>();
		result.setLotteryTicket(lotterys);

		return result;
	}

	public Lottery save(final Lottery lottery) {

		Lottery result;

		result = this.lotteryRepository.save(lottery);
		return result;
	}

	public Collection<Lottery> findAll() {

		Collection<Lottery> result = this.lotteryRepository.findAll();
		return result;
	}

	public Lottery findOne(final int lotteryId) {

		Assert.isTrue(lotteryId != 0);

		Lottery result = this.lotteryRepository.findOne(lotteryId);
		return result;
	}

}
