
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Lottery extends DomainEntity {

	public Lottery() {

	}


	private double	quantity;
	private double	percentageForPrizes;
	private Date	celebrationDate;
	private double	ticketCost;
	private String	lotteryName;


	@Digits(fraction = 2, integer = 12)
	@Min(0)
	public double getQuantity() {
		return this.quantity;
	}

	public void setQuantity(final double quantity) {
		this.quantity = quantity;
	}

	@Range(min = 0, max = 100)
	@Digits(fraction = 2, integer = 3)
	public double getPercentageForPrizes() {
		return this.percentageForPrizes;
	}

	public void setPercentageForPrizes(final double percentageForPrizes) {
		this.percentageForPrizes = percentageForPrizes;
	}

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCelebrationDate() {
		return this.celebrationDate;
	}

	public void setCelebrationDate(final Date celebrationDate) {
		this.celebrationDate = celebrationDate;
	}

	@Min(0)
	@Digits(fraction = 2, integer = 12)
	public double getTicketCost() {
		return this.ticketCost;
	}

	public void setTicketCost(final double ticketCost) {
		this.ticketCost = ticketCost;
	}

	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getLotteryName() {
		return this.lotteryName;
	}

	public void setLotteryName(final String lotteryName) {
		this.lotteryName = lotteryName;
	}


	// Relationships

	private Collection<LotteryTicket>	lotteryTickets;
	private GovernmentAgent				governmentAgent;
	private LotteryTicket				winnerTicket;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "lottery")
	public Collection<LotteryTicket> getLotteryTickets() {
		return this.lotteryTickets;
	}

	public void setLotteryTickets(final Collection<LotteryTicket> lotteryTickets) {
		this.lotteryTickets = lotteryTickets;
	}

	@ManyToOne(optional = false)
	@Valid
	public GovernmentAgent getGovernmentAgent() {
		return this.governmentAgent;
	}

	public void setGovernmentAgent(final GovernmentAgent governmentAgent) {
		this.governmentAgent = governmentAgent;
	}

	@OneToOne(optional = true)
	public LotteryTicket getWinnerTicket() {
		return this.winnerTicket;
	}

	public void setWinnerTicket(final LotteryTicket winnerTicket) {
		this.winnerTicket = winnerTicket;
	}

}
