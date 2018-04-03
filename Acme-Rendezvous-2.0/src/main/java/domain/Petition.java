
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "moment,adult")
})
public class Petition extends DomainEntity {

	// Constructors -----------------------------------------------------------

	public Petition() {
		super();
	}


	// Attributes -------------------------------------------------------------

	private String			name;
	private String			description;
	private String			picture;
	private Date			moment;
	private GpsCoordinate	gpsCoordinate;
	private boolean			adult;
	private boolean			finalVersion;
	private boolean			deleted;


	@NotBlank
	@SafeHtml
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	@URL
	@SafeHtml
	public String getPicture() {
		return this.picture;
	}

	public void setPicture(final String picture) {
		this.picture = picture;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	@Valid
	public GpsCoordinate getGpsCoordinate() {
		return this.gpsCoordinate;
	}

	public void setGpsCoordinate(final GpsCoordinate gpsCoordinate) {
		this.gpsCoordinate = gpsCoordinate;
	}

	public boolean getFinalVersion() {
		return this.finalVersion;
	}

	public void setFinalVersion(final boolean finalVersion) {
		this.finalVersion = finalVersion;
	}

	public boolean getAdult() {
		return this.adult;
	}

	public void setAdult(final boolean adult) {
		this.adult = adult;
	}

	public boolean getDeleted() {
		return this.deleted;
	}

	public void setDeleted(final boolean deleted) {
		this.deleted = deleted;
	}


	// Relationships ----------------------------------------------------------

	private Collection<Announcement>	announcements;
	private Collection<Comment>			comments;
	//private Collection<Question>		questions;
	private Citizen						organiser;
	private Collection<Citizen>			attendants;
	private Collection<Petition>		linkedRendezvouses;
	private Collection<Request>			requests;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Announcement> getAnnouncements() {
		return this.announcements;
	}

	public void setAnnouncements(final Collection<Announcement> announcements) {
		this.announcements = announcements;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Comment> getComments() {
		return this.comments;
	}

	public void setComments(final Collection<Comment> comments) {
		this.comments = comments;
	}

	//	@Valid
	//	@NotNull
	//	@OneToMany(mappedBy = "rendezvous")
	//	public Collection<Question> getQuestions() {
	//		return this.questions;
	//	}
	//
	//	public void setQuestions(final Collection<Question> questions) {
	//		this.questions = questions;
	//	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Citizen getOrganiser() {
		return this.organiser;
	}

	public void setOrganiser(final Citizen organiser) {
		this.organiser = organiser;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Citizen> getAttendants() {
		return this.attendants;
	}

	public void setAttendants(final Collection<Citizen> attendants) {
		this.attendants = attendants;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Petition> getLinkedRendezvouses() {
		return this.linkedRendezvouses;
	}

	public void setLinkedRendezvouses(final Collection<Petition> linkedRendezvouses) {
		this.linkedRendezvouses = linkedRendezvouses;
	}

	@Valid
	@NotNull
	@OneToMany(mappedBy = "rendezvous")
	public Collection<Request> getRequests() {
		return this.requests;
	}

	public void setRequests(final Collection<Request> requests) {
		this.requests = requests;
	}

}
