
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.BankAccountRepository;
import domain.Actor;
import domain.BankAccount;
import domain.EconomicTransaction;

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

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final BankAccount result = new BankAccount();

		final Collection<EconomicTransaction> credits = new ArrayList<EconomicTransaction>();
		final Collection<EconomicTransaction> debts = new ArrayList<EconomicTransaction>();

		final Actor actor = this.actorService.findOne(actorId);
		result.setActor(actor);
		result.setCredits(credits);
		result.setDebts(debts);

		return result;
	}

	public BankAccount save(final BankAccount bankAccount) {

		BankAccount result;

		result = this.bankAccountRepository.save(bankAccount);
		return result;
	}

	public Collection<BankAccount> findAll() {

		final Collection<BankAccount> result = this.bankAccountRepository.findAll();
		return result;
	}

	public BankAccount findOne(final int bankAccountId) {

		Assert.isTrue(bankAccountId != 0);

		final BankAccount result = this.bankAccountRepository.findOne(bankAccountId);
		return result;
	}

	public void delete(BankAccount bankAccount) {
		Assert.notNull(bankAccount);
		delete(bankAccount);

	}

}
