
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;

@Entity
@Access(AccessType.PROPERTY)
public class LotteryTicket extends DomainEntity {

	public LotteryTicket() {

	}


	private String	number;


	@NotNull
	@Pattern(regexp = "^\\d{6}$")
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getNumber() {
		return this.number;
	}

	public void setNumber(final String number) {
		this.number = number;
	}


	// Relationships

	private Citizen	citizen;
	private Lottery	lottery;


	@Valid
	@ManyToOne(optional = false)
	public Citizen getCitizen() {
		return this.citizen;
	}

	public void setCitizen(final Citizen citizen) {
		this.citizen = citizen;
	}

	@Valid
	@ManyToOne(optional = false)
	public Lottery getLottery() {
		return this.lottery;
	}

	public void setLottery(final Lottery lottery) {
		this.lottery = lottery;
	}

}
