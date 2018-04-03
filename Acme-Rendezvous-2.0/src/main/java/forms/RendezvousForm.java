package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.URL;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

public class RendezvousForm {

	// Constructors -----------------------------------------------------------

	public RendezvousForm() {
		super();
	}

	// Attributes -------------------------------------------------------------

	private int id;
	private String name;
	private String description;
	private String picture;
	private Date moment;
	private Double latitude;
	private Double longitude;
	private boolean adult;
	private boolean finalVersion;
	private boolean deleted;

	public int getId(){
		return id;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Date getMoment() {
		return moment;
	}

	public void setMoment(Date moment) {
		this.moment = moment;
	}

	@Valid
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}
	
	@Valid
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public boolean getFinalVersion() {
		return finalVersion;
	}

	public void setFinalVersion(boolean finalVersion) {
		this.finalVersion = finalVersion;
	}

	public boolean getAdult() {
		return adult;
	}

	public void setAdult(boolean adult) {
		this.adult = adult;
	}
	
	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

}
