
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "service_id")
})
public class Request extends DomainEntity {

	// Attributes -----------------------------------------------------------

	private CreditCard	creditCard;
	private String		comment;


	@Valid
	@NotNull
	public CreditCard getCreditCard() {
		return this.creditCard;
	}

	public void setCreditCard(final CreditCard creditCard) {
		this.creditCard = creditCard;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}


	// Relationships --------------------------------------------------------

	private Petition	rendezvous;
	private Service		service;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Petition getRendezvous() {
		return this.rendezvous;
	}

	public void setRendezvous(final Petition rendezvous) {
		this.rendezvous = rendezvous;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Service getService() {
		return this.service;
	}

	public void setService(final Service service) {
		this.service = service;
	}

}
