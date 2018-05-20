
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
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class BankAgent extends Actor {

	// Constructors

	public BankAgent() {
		super();
	}


	// Attributes

	private boolean	canCreateMoney;
	private String	bankCode;


	@NotBlank
	@Pattern(regexp = "^\\d{4}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getBankCode() {
		return this.bankCode;
	}

	public void setBankCode(final String bankCode) {
		this.bankCode = bankCode;
	}

	public boolean getCanCreateMoney() {
		return this.canCreateMoney;
	}

	public void setCanCreateMoney(final Boolean canCreateMoney) {
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
