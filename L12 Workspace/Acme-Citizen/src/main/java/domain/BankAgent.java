
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class BankAgent extends Actor {

	// Constructors

	public BankAgent() {
		super();
	}

	// Relationships

	private Collection<BankAccount> bankAccounts;

	@Valid
	@NotNull
	@OneToMany(mappedBy = "bankAgent")
	public Collection<BankAccount> getBankAccounts() {
		return this.bankAccounts;
	}

	public void setBankAccounts(final Collection<BankAccount> bankAccounts) {
		this.bankAccounts = bankAccounts;
	}

}
