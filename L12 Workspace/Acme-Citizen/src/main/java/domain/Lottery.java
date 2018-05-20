
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;

@Entity
@Access(AccessType.PROPERTY)
public class Lottery extends DomainEntity {

	public Lottery() {

	}


	private Double	quantity;
	private Double	percentageForPrizes;
	private Date	celebrationDate;
	private Double	ticketCost;
	private String	lotteryName;


	@NotNull
	@Digits(fraction = 2, integer = 12)
	@Min(0)
	public Double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Double quantity) {
		this.quantity = quantity;
	}

	@NotNull
	@Range(min = 0, max = 100)
	@Digits(fraction = 2, integer = 3)
	public Double getPercentageForPrizes() {
		return this.percentageForPrizes;
	}

	public void setPercentageForPrizes(Double percentageForPrizes) {
		this.percentageForPrizes = percentageForPrizes;
	}

	@NotNull
	@Past
	public Date getCelebrationDate() {
		return this.celebrationDate;
	}

	public void setCelebrationDate(Date celebrationDate) {
		this.celebrationDate = celebrationDate;
	}

	@Min(0)
	@NotNull
	@Digits(fraction = 2, integer = 12)
	public Double getTicketCost() {
		return this.ticketCost;
	}

	public void setTicketCost(Double ticketCost) {
		this.ticketCost = ticketCost;
	}

	@NotBlank
	public String getLotteryName() {
		return this.lotteryName;
	}

	public void setLotteryName(String lotteryName) {
		this.lotteryName = lotteryName;
	}


	// Relationships

	private Collection<LotteryTicket>	lotteryTickets;
	private GovernmentAgent				governmentAgent;
	private LotteryTicket				winnerTicket;


	@OneToMany
	public Collection<LotteryTicket> getLotteryTickets() {
		return this.lotteryTickets;
	}

	public void setLotteryTickets(Collection<LotteryTicket> lotteryTickets) {
		this.lotteryTickets = lotteryTickets;
	}

	@ManyToOne
	@Valid
	@NotNull
	public GovernmentAgent getGovernmentAgent() {
		return this.governmentAgent;
	}

	public void setGovernmentAgent(GovernmentAgent governmentAgent) {
		this.governmentAgent = governmentAgent;
	}

	@OneToOne(optional = false)
	public LotteryTicket getWinnerTicket() {
		return this.winnerTicket;
	}

	public void setWinnerTicket(LotteryTicket winnerTicket) {
		this.winnerTicket = winnerTicket;
	}

}
