
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	// Constructors

	public Configuration() {
		super();
	}


	// Attributes

	private String	defaultCountryCode;
	private String	countryFlag;
	private int		numberParliamentSeats;


	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDefaultCountryCode() {
		return this.defaultCountryCode;
	}

	public void setDefaultCountryCode(final String defaultCountryCode) {
		this.defaultCountryCode = defaultCountryCode;
	}

	@NotBlank
	@URL
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getCountryFlag() {
		return this.countryFlag;
	}

	public void setCountryFlag(final String countryFlag) {
		this.countryFlag = countryFlag;
	}

	@Min(0)
	public int getNumberParliamentSeats() {
		return this.numberParliamentSeats;
	}

	public void setNumberParliamentSeats(final int numberParliamentSeats) {
		this.numberParliamentSeats = numberParliamentSeats;
	}


	//Relationships

	private Collection<WelcomeMessage>	welcomeMessages;


	@Valid
	@NotNull
	@OneToMany
	public Collection<WelcomeMessage> getWelcomeMessages() {
		return this.welcomeMessages;
	}

	public void setWelcomeMessages(final Collection<WelcomeMessage> welcomeMessages) {
		this.welcomeMessages = welcomeMessages;
	}

}
