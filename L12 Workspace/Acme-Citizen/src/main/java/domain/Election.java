
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Election extends Commentable {

	// Constructor ----------------------------------------------------------------------------
	public Election() {
		super();
	}


	// Attributes ------------------------------------------------------------------------------

	private Date	celebrationDate;
	private Date	candidatureDate;
	private String	description;


	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCelebrationDate() {
		return this.celebrationDate;
	}

	public void setCelebrationDate(final Date celebrationDate) {
		this.celebrationDate = celebrationDate;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCandidatureDate() {
		return this.candidatureDate;
	}

	public void setCandidatureDate(final Date candidatureDate) {
		this.candidatureDate = candidatureDate;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}


	// Relashionships ----------------------------------------------------------------

	private GovernmentAgent			governmentAgent;
	private Collection<Citizen>		citizens;
	private Collection<Candidature>	candidatures;


	@Valid
	@ManyToOne(optional = false)
	public GovernmentAgent getGovernmentAgent() {
		return this.governmentAgent;
	}

	public void setGovernmentAgent(final GovernmentAgent governmentAgent) {
		this.governmentAgent = governmentAgent;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Citizen> getCitizens() {
		return this.citizens;
	}

	public void setCitizens(final Collection<Citizen> citizens) {
		this.citizens = citizens;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "election")
	public Collection<Candidature> getCandidatures() {
		return this.candidatures;
	}

	public void setCandidatures(final Collection<Candidature> candidatures) {
		this.candidatures = candidatures;
	}

}
