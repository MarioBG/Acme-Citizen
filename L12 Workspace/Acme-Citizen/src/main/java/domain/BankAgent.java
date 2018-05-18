
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class BankAgent extends Actor {

	// Constructors

	public BankAgent() {
		super();
	}

	// Relationships

	//private Collection<BankAccount> bankAccounts;						//DESCOMENTAR CUANDO SE HAGAN LAS CLASES DE DOMINIO

	/*
	 * 
	 * @Valid
	 * 
	 * @NotNull
	 * 
	 * @OneToMany(mappedBy = "bankAgent")
	 * public Collection<BankAccount> getBankAccounts() {
	 * return this.bankAccounts;
	 * }
	 * 
	 * public void setBankAccounts(final Collection<BankAccount> bankAccounts) {
	 * this.bankAccounts = bankAccounts;
	 * }
	 */

}
