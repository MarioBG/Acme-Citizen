
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Entity
@Access(AccessType.PROPERTY)
public class GovernmentAgent extends Actor {

	// Constructors

	public GovernmentAgent() {
		super();
	}


	// Relationships

	//private Collection<Lottery> lotteries;						//DESCOMENTAR CUANDO SE HAGAN LAS CLASES DE DOMINIO
	private Collection<Petition>	petitions;
	private Collection<Election>	elections;


	//	@Valid
	//	@NotNull
	//	@OneToMany(mappedBy = "governmentAgent")
	//	public Collection<Lottery> getLotteries() {
	//		return this.lotteries;
	//	}
	//
	//	public void setLotteries(final Collection<Lottery> lotteries) {
	//		this.lotteries = lotteries;
	//	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "governmentAgents")
	public Collection<Petition> getPetitions() {
		return this.petitions;
	}

	public void setPetitions(final Collection<Petition> petitions) {
		this.petitions = petitions;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "governmentAgent")
	public Collection<Election> getElections() {
		return this.elections;
	}

	public void setElection(final Collection<Election> elections) {
		this.elections = elections;
	}

}
