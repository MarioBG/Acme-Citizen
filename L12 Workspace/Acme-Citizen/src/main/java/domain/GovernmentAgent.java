
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class GovernmentAgent extends Actor {

	// Constructors

	public GovernmentAgent() {
		super();
	}


	// Attributes

	private boolean	canOrganiseElection;
	private boolean	canCreateMoney;
	private String	registerCode;


	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{6}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getRegisterCode() {
		return this.registerCode;
	}

	public void setRegisterCode(final String registerCode) {
		this.registerCode = registerCode;
	}

	public boolean getCanCreateMoney() {
		return this.canCreateMoney;
	}

	public void setCanCreateMoney(final boolean canCreateMoney) {
		this.canCreateMoney = canCreateMoney;
	}

	public boolean getCanOrganiseElection() {
		return this.canOrganiseElection;
	}

	public void setCanOrganiseElection(final boolean canOrganiseElection) {
		this.canOrganiseElection = canOrganiseElection;
	}


	// Relationships

	private Collection<Lottery>		lotteries;
	private Collection<Petition>	petitions;
	private Collection<Election>	elections;
	private Collection<Chirp>		chirps;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "governmentAgent")
	public Collection<Lottery> getLotteries() {
		return this.lotteries;
	}

	public void setLotteries(final Collection<Lottery> lotteries) {
		this.lotteries = lotteries;
	}

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

	public void setElections(final Collection<Election> elections) {
		this.elections = elections;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "governmentAgent")
	public Collection<Chirp> getChirps() {
		return this.chirps;
	}

	public void setChirps(final Collection<Chirp> chrips) {
		this.chirps = chrips;
	}

	

}
