
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.Min;

@Entity
@Access(AccessType.PROPERTY)
public class Candidate extends DomainEntity {

	// Constructor ----------------------------------------------------------------------------
	public Candidate() {
		super();
	}


	// Attributes ------------------------------------------------------------------------------

	private int	listOrder;


	@Min(0)
	public int getListOrder() {
		return this.listOrder;
	}

	public void setListOrder(final int listOrder) {
		this.listOrder = listOrder;
	}


	// Relashionships ----------------------------------------------------------------

	private Citizen		citizen;
	private Candidature	candidature;


	@Valid
	@ManyToOne(optional = false)
	public Citizen getCitizen() {
		return this.citizen;
	}

	public void setCitizen(final Citizen citizen) {
		this.citizen = citizen;
	}

	@Valid
	@ManyToOne(optional = false)
	public Candidature getCandidature() {
		return this.candidature;
	}

	public void setCandidature(final Candidature candidature) {
		this.candidature = candidature;
	}

}
