
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
public class Citizen extends Actor {

	// Constructors

	public Citizen() {
		super();
	}


	// Relationships

	private Collection<LotteryTicket>	lotteryTickets; // DESCOMENTAR CUANDO SE HAGAN LAS CLASES DE DOMINIO
	private Collection<Petition>		petitions;
	private Collection<Election>		elections;
	private Collection<Candidate>		candidates;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "citizen")
	public Collection<LotteryTicket> getLotteryTickets() {
		return this.lotteryTickets;
	}

	public void setLotteryTickets(final Collection<LotteryTicket> lotteryTickets) {
		this.lotteryTickets = lotteryTickets;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "citizen")
	public Collection<Petition> getPetitions() {
		return this.petitions;
	}

	public void setPetitions(final Collection<Petition> petitions) {
		this.petitions = petitions;
	}

	@Valid
	@NotNull
	@ManyToMany(mappedBy = "citizens")
	public Collection<Election> getElections() {
		return this.elections;
	}

	public void setElections(final Collection<Election> elections) {
		this.elections = elections;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "citizen")
	public Collection<Candidate> getCandidates() {
		return this.candidates;
	}

	public void setCandidates(Collection<Candidate> candidates) {
		this.candidates = candidates;
	}

}
