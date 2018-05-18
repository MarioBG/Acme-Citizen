package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.BankAccount;
import domain.EconomicTransaction;
import repositories.BankAccountRepository;

@Service
@Transactional
public class BankAccountService {

	// Managed repository

	@Autowired
	private BankAccountRepository bankAccountRepository;

	// Supporting services

	@Autowired
	private ActorService actorService;

	// Constructors

	public BankAccountService() {
		super();
	}

	// Simple CRUD methods

	public BankAccount create(final int actorId) {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final BankAccount result = new BankAccount();

		Collection<EconomicTransaction> credit = new ArrayList<EconomicTransaction>();
		Collection<EconomicTransaction> debt = new ArrayList<EconomicTransaction>();

		Actor actor = this.actorService.findOne(actorId);
		result.setActor(actor);
		result.setCredit(credit);
		result.setDebt(debt);

		return result;
	}

	public BankAccount save(final BankAccount bankAccount) {

		BankAccount result;

		result = this.bankAccountRepository.save(bankAccount);
		return result;
	}

	public Collection<BankAccount> findAll() {

		Collection<BankAccount> result = this.bankAccountRepository.findAll();
		return result;
	}

	public BankAccount findOne(final int bankAccountId) {

		Assert.isTrue(bankAccountId != 0);

		BankAccount result = this.bankAccountRepository.findOne(bankAccountId);
		return result;
	}

}
