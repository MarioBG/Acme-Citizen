
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

@Entity
@Access(AccessType.PROPERTY)
public class GovernmentAgent extends Actor {

	// Constructors

	public GovernmentAgent() {
		super();
	}


	// Attributes

	private Boolean	canOrganiseElection;
	private Boolean	canCreateMoney;
	private String	registerCode;


	@NotBlank
	@Pattern(regexp = "^[a-zA-Z]{6}$")
	public String getRegisterCode() {
		return this.registerCode;
	}

	public void setRegisterCode(String registerCode) {
		this.registerCode = registerCode;
	}

	public Boolean getCanCreateMoney() {
		return this.canCreateMoney;
	}

	public void setCanCreateMoney(Boolean canCreateMoney) {
		this.canCreateMoney = canCreateMoney;
	}

	public Boolean getCanOrganiseElection() {
		return this.canOrganiseElection;
	}

	public void setCanOrganiseElection(Boolean canOrganiseElection) {
		this.canOrganiseElection = canOrganiseElection;
	}


	// Relationships

	private Collection<Lottery>		lotteries;	//DESCOMENTAR CUANDO SE HAGAN LAS CLASES DE DOMINIO
	private Collection<Petition>	petitions;
	private Collection<Election>	elections;


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

}
