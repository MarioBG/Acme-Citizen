
package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EconomicTransactionRepository;
import domain.Actor;
import domain.BankAccount;
import domain.BankAgent;
import domain.EconomicTransaction;
import domain.GovernmentAgent;

@Service
@Transactional
public class EconomicTransactionService {

	// Managed repository

	@Autowired
	private EconomicTransactionRepository	economicTransactionRepository;

	// Supporting services

	@Autowired
	private ActorService					actorService;


	// Constructors

	public EconomicTransactionService() {
		super();
	}

	// Simple CRUD methods

	public EconomicTransaction create() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final EconomicTransaction result = new EconomicTransaction();
		final Date date = new Date(System.currentTimeMillis() - 1);

		result.setTransactionMoment(date);
		result.setDebtor(principal.getBankAccount());
		result.setDoMoney(false);

		return result;
	}

	public EconomicTransaction createMoney() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final EconomicTransaction result = new EconomicTransaction();
		final Date date = new Date(System.currentTimeMillis() - 1);

		result.setTransactionMoment(date);
		result.setDebtor(principal.getBankAccount());
		result.setDoMoney(true);

		return result;
	}

	public EconomicTransaction save(final EconomicTransaction economicTransaction) {

		Assert.notNull(economicTransaction);

		EconomicTransaction result;

		result = this.economicTransactionRepository.save(economicTransaction);
		Assert.notNull(result.getDebtor(), "economicTransaction.commit.error");
		Assert.notNull(result.getCreditor(), "economicTransaction.creditor.error");

		this.doTransaction(result);

		return result;
	}

	public EconomicTransaction save2(final EconomicTransaction economicTransaction) {

		Assert.notNull(economicTransaction);

		EconomicTransaction result;

		result = this.economicTransactionRepository.save(economicTransaction);

		this.doMoney(result);

		return result;
	}

	public Collection<EconomicTransaction> findAll() {

		final Collection<EconomicTransaction> result = this.economicTransactionRepository.findAll();
		return result;
	}

	public EconomicTransaction findOne(final int economicTransactionId) {

		Assert.isTrue(economicTransactionId != 0);

		final EconomicTransaction result = this.economicTransactionRepository.findOne(economicTransactionId);
		return result;
	}

	public Collection<EconomicTransaction> findCreditorTransactionByActorId(final int actorId) {
		return this.economicTransactionRepository.findCreditorTransactionByActorId(actorId);

	}

	public Collection<EconomicTransaction> findDebtorTransactionByActorId(final int actorId) {
		return this.economicTransactionRepository.findDebtorTransactionByActorId(actorId);

	}

	private void doTransaction(final EconomicTransaction result) {
		final Double transfer = result.getQuantity();
		final Actor creditor = result.getCreditor().getActor();
		final Actor debtor = result.getDebtor().getActor();

		final Double moneyReceived = creditor.getBankAccount().getMoney() + transfer;
		final Double moneySender = debtor.getBankAccount().getMoney() - transfer;
		if (moneySender >= 0) {
			creditor.getBankAccount().setMoney(moneyReceived);
			debtor.getBankAccount().setMoney(moneySender);
		} else
			this.economicTransactionRepository.delete(result);

	}

	private void doMoney(final EconomicTransaction result) {
		final Double transfer = result.getQuantity();
		final BankAccount creditor = result.getCreditor();
		BankAgent bankAgent = null;
		GovernmentAgent ga = null;
		try {
			bankAgent = (BankAgent) this.actorService.findByPrincipal();

		} catch (final Exception e) {
		}

		try {
			ga = (GovernmentAgent) this.actorService.findByPrincipal();
		} catch (final Exception e) {
		}

		final Double moneyReceived = creditor.getMoney() + transfer;
		if (bankAgent != null) {
			if (bankAgent.getCanCreateMoney())
				creditor.setMoney(moneyReceived);
		} else if (ga != null) {
			if (ga.getCanCreateMoney())
				creditor.setMoney(moneyReceived);
		} else
			this.economicTransactionRepository.delete(result);

	}

	public boolean checkMoney(final EconomicTransaction economicTransaction) {
		if (economicTransaction.getDebtor().getMoney() >= economicTransaction.getQuantity())
			return true;
		else
			return false;

	}

	public Collection<EconomicTransaction> findBankAgentDoMoney() {
		return this.economicTransactionRepository.findBankAgentDoMoney();
	}

	public Collection<EconomicTransaction> findGovernmentAgentDoMoney() {
		return this.economicTransactionRepository.findGovernmentAgentDoMoney();
	}

}
