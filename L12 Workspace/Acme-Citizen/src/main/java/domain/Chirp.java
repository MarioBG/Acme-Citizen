
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
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
public class Chirp extends DomainEntity implements Comparable<Chirp> {

	// Constructors

	public Chirp() {
		super();
	}


	// Attributes

	private String	title;
	private String	content;
	private Date	publicationMoment;
	private String	image;
	private String	link;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getContent() {
		return this.content;
	}

	public void setContent(final String content) {
		this.content = content;
	}

	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getPublicationMoment() {
		return this.publicationMoment;
	}

	public void setPublicationMoment(final Date publicationMoment) {
		this.publicationMoment = publicationMoment;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getImage() {
		return this.image;
	}

	public void setImage(final String image) {
		this.image = image;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLink() {
		return this.link;
	}

	public void setLink(final String link) {
		this.link = link;
	}


	// Relationships

	private GovernmentAgent	governmentAgent;


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public GovernmentAgent getGovernmentAgent() {
		return this.governmentAgent;
	}

	public void setGovernmentAgent(final GovernmentAgent governmentAgent) {
		this.governmentAgent = governmentAgent;
	}

	// Compare to

	@Override
	public int compareTo(final Chirp chirp) {
		final float chirpCompare = chirp.getPublicationMoment().getTime();

		return Math.round(chirpCompare - this.publicationMoment.getTime());
	}

}
