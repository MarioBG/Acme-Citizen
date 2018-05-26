package forms;

import java.util.Date;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

public class EconomicTransacionForm {

	private int id;
	private double quantity;
	private Date transactionMoment;
	private String concept;

	public EconomicTransacionForm() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public double getQuantity() {
		return quantity;
	}

	public void setQuantity(double quantity) {
		this.quantity = quantity;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Date getTransactionMoment() {
		return transactionMoment;
	}

	public void setTransactionMoment(Date transactionMoment) {
		this.transactionMoment = transactionMoment;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getConcept() {
		return concept;
	}

	public void setConcept(String concept) {
		this.concept = concept;
	}

}
