
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.GovernmentAgent;
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

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final Lottery result = new Lottery();

		final Collection<LotteryTicket> lotterys = new ArrayList<LotteryTicket>();
		result.setLotteryTickets(lotterys);
		result.setGovernmentAgent((GovernmentAgent) principal);

		return result;
	}

	public Lottery save(final Lottery lottery) {
		Assert.notNull(lottery);
		Lottery result;

		Date date = new Date();
		if (lottery.getCelebrationDate().before(date)) {
			Assert.isTrue(lottery.getCelebrationDate().after(date), "commit.error.date");

		}
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

	public Collection<Lottery> findAllAfterToday() {
		return this.lotteryRepository.findAllAfterToday();
	}

	public Collection<Lottery> getLotteryByGovernmentAgentId(int id) {
		return this.lotteryRepository.getLotteryByGovernmentAgentId(id);
	}

	public void delete(Lottery lottery) {
		Assert.notNull(lottery);
		delete(lottery);

	}

}
