package services;

import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.BankAccount;
import domain.BankAgent;
import domain.EconomicTransaction;
import domain.GovernmentAgent;
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
		Assert.isTrue(principal != null);

		final EconomicTransaction result = new EconomicTransaction();
		Date date = new Date(System.currentTimeMillis() - 1);

		result.setTransactionMoment(date);
		result.setDebtor(principal.getBankAccount());
		result.setDoMoney(false);

		return result;
	}

	public EconomicTransaction createMoney() {

		Actor principal = actorService.findByPrincipal();
		Assert.isTrue(principal != null);

		final EconomicTransaction result = new EconomicTransaction();
		Date date = new Date(System.currentTimeMillis() - 1);

		result.setTransactionMoment(date);
		result.setDebtor(principal.getBankAccount());
		result.setDoMoney(true);

		return result;
	}

	public EconomicTransaction save(EconomicTransaction economicTransaction) {

		Assert.notNull(economicTransaction);

		EconomicTransaction result;

		result = this.economicTransactionRepository.save(economicTransaction);
		Assert.notNull(result.getDebtor(), "economicTransaction.commit.error");

		doTransaction(result);

		return result;
	}

	public EconomicTransaction save2(EconomicTransaction economicTransaction) {

		Assert.notNull(economicTransaction);

		EconomicTransaction result;

		result = this.economicTransactionRepository.save(economicTransaction);

		doMoney(result);

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

	private void doTransaction(EconomicTransaction result) {
		Double transfer = result.getQuantity();
		Actor creditor = result.getCreditor().getActor();
		Actor debtor = result.getDebtor().getActor();

		Double moneyReceived = creditor.getBankAccount().getMoney() + transfer;
		Double moneySender = debtor.getBankAccount().getMoney() - transfer;
		if (moneySender >= 0) {
			creditor.getBankAccount().setMoney(moneyReceived);
			debtor.getBankAccount().setMoney(moneySender);
		} else {
			this.economicTransactionRepository.delete(result);
		}

	}

	private void doMoney(EconomicTransaction result) {
		Double transfer = result.getQuantity();
		BankAccount creditor = result.getCreditor();
		BankAgent bankAgent = null;
		GovernmentAgent ga = null;
		try {
			bankAgent = (BankAgent) this.actorService.findByPrincipal();
			ga = (GovernmentAgent) this.actorService.findByPrincipal();

		} catch (Exception e) {
		}

		Double moneyReceived = creditor.getMoney() + transfer;
		if (bankAgent != null) {
			if (bankAgent.getCanCreateMoney()) {
				creditor.setMoney(moneyReceived);
			}

		} else if (ga != null) {
			if (ga.getCanCreateMoney()) {
				creditor.setMoney(moneyReceived);

			}
		} else {

			this.economicTransactionRepository.delete(result);
		}

	}

	public boolean checkMoney(EconomicTransaction economicTransaction) {
		if (economicTransaction.getDebtor().getMoney() >= economicTransaction.getQuantity()) {
			return true;

		} else {
			return false;
		}

	}

	public Collection<EconomicTransaction> findCreatedMoneyTransaction() {
		return this.economicTransactionRepository.findCreatedMoneyTransaction();
	}

}
