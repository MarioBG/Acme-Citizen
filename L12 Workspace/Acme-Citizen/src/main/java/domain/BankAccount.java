
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Access(AccessType.PROPERTY)
public class BankAccount extends DomainEntity {

	public BankAccount() {

	}


	private Double	money;
	private String	accountNumber;


	@Digits(fraction = 2, integer = 12)
	@NotNull
	@Min(0)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Pattern(regexp = "^\\d{14}$")
	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}


	// Relationships
	private Collection<EconomicTransaction>	credits;
	private Collection<EconomicTransaction>	debts;
	private BankAgent						bankAgent;
	private Actor							actor;


	@OneToMany
	@Valid
	public Collection<EconomicTransaction> getCredits() {
		return this.credits;
	}

	public void setCredits(Collection<EconomicTransaction> credits) {
		this.credits = credits;
	}

	@Valid
	@OneToMany
	public Collection<EconomicTransaction> getDebts() {
		return this.debts;
	}

	public void setDebts(Collection<EconomicTransaction> debts) {
		this.debts = debts;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public BankAgent getBankAgent() {
		return this.bankAgent;
	}

	public void setBankAgent(BankAgent bankAgent) {
		this.bankAgent = bankAgent;
	}

	@OneToOne
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(Actor actor) {
		this.actor = actor;
	}

}
