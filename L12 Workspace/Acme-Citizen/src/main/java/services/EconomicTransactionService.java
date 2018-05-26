package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.EconomicTransaction;
import repositories.EconomicTransactionRepository;

@Service
@Transactional
public class EconomicTransactionService {

	// Managed repository

	@Autowired
	private EconomicTransactionRepository economicTransactionRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	// Constructors

	public EconomicTransactionService() {
		super();
	}

	// Simple CRUD methods

	public EconomicTransaction create() {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final EconomicTransaction result = new EconomicTransaction();

		Date date = new Date();
		result.setTransactionMoment(date);

		result.setCreditor(principal.getBankAccount());

		return result;
	}

	public EconomicTransaction save(final EconomicTransaction economicTransaction) {

		EconomicTransaction result;

		result = this.economicTransactionRepository.save(economicTransaction);
		return result;
	}

	public Collection<EconomicTransaction> findAll() {

		Collection<EconomicTransaction> result = this.economicTransactionRepository.findAll();
		return result;
	}

	public EconomicTransaction findOne(final int economicTransactionId) {

		Assert.isTrue(economicTransactionId != 0);

		EconomicTransaction result = this.economicTransactionRepository.findOne(economicTransactionId);
		return result;
	}

	public Collection<EconomicTransaction> findCreditorTransactionByActorId(int actorId) {
		return this.economicTransactionRepository.findCreditorTransactionByActorId(actorId);

	}

	public Collection<EconomicTransaction> findDebtorTransactionByActorId(int actorId) {
		return this.economicTransactionRepository.findDebtorTransactionByActorId(actorId);

	}

}
