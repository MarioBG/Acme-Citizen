package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.springframework.format.annotation.DateTimeFormat;


@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {@Index(columnList = "rendezvous_Id") })
public class Announcement extends DomainEntity{
	
	// Constructor 
	
	public Announcement(){
		super();
	}

	// Attributes
	
	private Date moment;
	private String title;
	private String description;
	
	@Past
	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return moment;
	}
	
	public void setMoment(Date moment) {
		this.moment = moment;
	}
	
	@NotBlank
	@SafeHtml
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	@NotBlank
	@SafeHtml
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	// Relationships  
	
	private Petition rendezvous;
	
	@Valid
	@NotNull
	@ManyToOne(optional=false)
	public Petition getRendezvous(){
		return rendezvous;
	}
	
	public void setRendezvous(Petition rendezvous){
		this.rendezvous = rendezvous;
	}
	 
	
	
	
}
