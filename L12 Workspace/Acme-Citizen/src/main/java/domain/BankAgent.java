
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class BankAgent extends Actor {

	// Constructors

	public BankAgent() {
		super();
	}


	// Attributes

	private Boolean	canCreateMoney;
	private String	bankCode;


	@NotBlank
	@Pattern(regexp = "^\\d{4}$")
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public Boolean getCanCreateMoney() {
		return this.canCreateMoney;
	}

	public void setCanCreateMoney(Boolean canCreateMoney) {
		this.canCreateMoney = canCreateMoney;
	}


	// Relationships

	private Collection<BankAccount>	carriedBankAccounts;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "bankAgent")
	public Collection<BankAccount> getCarriedBankAccounts() {
		return this.carriedBankAccounts;
	}

	public void setCarriedBankAccounts(final Collection<BankAccount> bankAccounts) {
		this.carriedBankAccounts = bankAccounts;
	}

}
