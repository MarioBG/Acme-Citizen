
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

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class BankAccount extends DomainEntity {

	public BankAccount() {

	}


	private double	money;
	private String	accountNumber;


	@Digits(fraction = 2, integer = 12)
	@Min(0)
	public double getMoney() {
		return this.money;
	}

	public void setMoney(final double money) {
		this.money = money;
	}

	@NotBlank
	@Pattern(regexp = "^\\d{14}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getAccountNumber() {
		return this.accountNumber;
	}

	public void setAccountNumber(final String accountNumber) {
		this.accountNumber = accountNumber;
	}


	// Relationships
	private Collection<EconomicTransaction>	credits;
	private Collection<EconomicTransaction>	debts;
	private BankAgent						bankAgent;
	private Actor							actor;


	@OneToMany(mappedBy = "creditor")
	@NotNull
	@Valid
	public Collection<EconomicTransaction> getCredits() {
		return this.credits;
	}

	public void setCredits(final Collection<EconomicTransaction> credits) {
		this.credits = credits;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "debtor")
	public Collection<EconomicTransaction> getDebts() {
		return this.debts;
	}

	public void setDebts(final Collection<EconomicTransaction> debts) {
		this.debts = debts;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public BankAgent getBankAgent() {
		return this.bankAgent;
	}

	public void setBankAgent(final BankAgent bankAgent) {
		this.bankAgent = bankAgent;
	}

	@Valid
	@OneToOne(optional = false)
	public Actor getActor() {
		return this.actor;
	}

	public void setActor(final Actor actor) {
		this.actor = actor;
	}

}
