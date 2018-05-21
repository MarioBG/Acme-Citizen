
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Petition extends Commentable {

	// Constructor ----------------------------------------------------------------------------
	public Petition() {
		super();
	}


	// Attributes ------------------------------------------------------------------------------

	private String	name;
	private String	description;
	private String	picture;
	private Date	creationMoment;
	private boolean	finalVersion;
	private boolean	resolved;
	private boolean	deleted;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getCreationMoment() {
		return this.creationMoment;
	}

	public void setCreationMoment(final Date creationMoment) {
		this.creationMoment = creationMoment;
	}

	public boolean getFinalVersion() {
		return this.finalVersion;
	}

	public void setFinalVersion(final boolean finalVersion) {
		this.finalVersion = finalVersion;
	}

	public boolean getResolved() {
		return this.resolved;
	}

	public void setResolved(final boolean resolved) {
		this.resolved = resolved;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}


	// Relashionships ----------------------------------------------------------------

	private Collection<GovernmentAgent>	governmentAgents;
	private Citizen						citizen;


	@Valid
	@NotNull
	@ManyToMany
	public Collection<GovernmentAgent> getGovernmentAgents() {
		return this.governmentAgents;
	}

	public void setGovernmentAgents(final Collection<GovernmentAgent> governmentAgents) {
		this.governmentAgents = governmentAgents;
	}

	@Valid
	@ManyToOne(optional = false)
	public Citizen getCitizen() {
		return this.citizen;
	}

	public void setCitizen(final Citizen citizen) {
		this.citizen = citizen;
	}

}
