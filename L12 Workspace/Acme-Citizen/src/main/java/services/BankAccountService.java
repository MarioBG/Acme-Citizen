
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import domain.Actor;
import domain.BankAccount;
import domain.BankAgent;
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
	private BankAgentService bankAgentService;

	// Constructors

	public BankAccountService() {
		super();
	}

	// Simple CRUD methods

	public BankAccount create() {

		final BankAgent principal = this.bankAgentService.findByPrincipal();
		Assert.isTrue(principal != null);

		final BankAccount result = new BankAccount();

		final Collection<EconomicTransaction> credits = new ArrayList<EconomicTransaction>();
		final Collection<EconomicTransaction> debts = new ArrayList<EconomicTransaction>();

		result.setCredits(credits);
		result.setDebts(debts);
		result.setMoney(0.0);
		result.setAccountNumber(getAccountNumber());
		result.setBankAgent(principal);

		return result;
	}

	public BankAccount save(final BankAccount bankAccount) {

		BankAccount result;

		result = this.bankAccountRepository.save(bankAccount);
		try {
			Actor actor = bankAccount.getActor();
			actor.setBankAccount(result);
		} catch (Exception e) {
			// TODO: handle exception
		}

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

	private String getAccountNumber() {
		String numberAccount = asignNumber();

		for (BankAccount bk : this.bankAccountRepository.findAll()) {
			while (bk.getAccountNumber().equals(numberAccount)) {
				numberAccount = asignNumber();
			}

		}
		return numberAccount;

	}

	private String asignNumber() {
		final BankAgent principal = this.bankAgentService.findByPrincipal();
		String code = principal.getBankCode();
		Integer num1 = (int) (Math.random() * 100000);
		Integer num2 = (int) (Math.random() * 100000);

		String part1 = num1.toString();
		String part2 = num2.toString();

		if (part1.length() == 1) {
			part1 = "0000" + part1;
		} else if (part1.length() == 2) {
			part1 = "000" + part1;
		} else if (part1.length() == 3) {
			part1 = "00" + part1;
		} else if (part1.length() == 4) {
			part1 = "0" + part1;
		}
		if (part2.length() == 1) {
			part2 = "0000" + part2;
		} else if (part2.length() == 2) {
			part2 = "000" + part2;
		} else if (part2.length() == 3) {
			part2 = "00" + part2;
		} else if (part2.length() == 4) {
			part2 = "0" + part2;
		}
		String numberAccount = code + part1 + part2;

		return numberAccount;

	}

}
