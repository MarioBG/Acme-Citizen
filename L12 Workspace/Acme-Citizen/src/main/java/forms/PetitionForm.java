
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

public class PetitionForm {

	private int		id;
	private int		citizenId;
	private String	name;
	private String	description;
	private String	picture;
	private Date	creationMoment;
	private boolean	finalVersion;
	private boolean	resolved;
	private boolean	deleted;


	public PetitionForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getCitizenId() {
		return this.citizenId;
	}

	public void setCitizenId(final int citizenId) {
		this.citizenId = citizenId;
	}

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

	@NotNull
	@Past
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

}
