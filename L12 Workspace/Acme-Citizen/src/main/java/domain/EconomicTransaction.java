package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class EconomicTransaction extends DomainEntity {

	public EconomicTransaction() {

	}

	private double quantity;
	private Date transactionMoment;
	private String concept;

	// Relationships

	@Digits(fraction = 2, integer = 12)
	@NotNull
	@Min(0)
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@Past
	@NotNull
	public Date getTransactionMoment() {
		return transactionMoment;
	}

	public void setTransactionMoment(Date transactionMoment) {
		this.transactionMoment = transactionMoment;
	}

	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

	private BankAccount creditor;
	private BankAccount debtor;

	@ManyToOne(optional = true)
	@Valid
	public BankAccount getCreditor() {
		return creditor;
	}

	public void setCreditor(BankAccount creditor) {
		this.creditor = creditor;
	}

	@Valid
	@ManyToOne(optional = true)
	public BankAccount getDebtor() {
		return debtor;
	}

	public void setDebtor(BankAccount debtor) {
		this.debtor = debtor;
	}

}
