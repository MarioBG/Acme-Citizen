
package forms;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.springframework.format.annotation.DateTimeFormat;

public class ElectionForm {

	private int		id;
	private int		governmentAgentId;
	private Date	celebrationDate;
	private Date	candidatureDate;
	private String	description;


	public ElectionForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getGovernmentAgentId() {
		return this.governmentAgentId;
	}

	public void setGovernmentAgentId(final int governmentAgentId) {
		this.governmentAgentId = governmentAgentId;
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

	@NotNull
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getCandidatureDate() {
		return this.candidatureDate;
	}

	public void setCandidatureDate(final Date candidatureDate) {
		this.candidatureDate = candidatureDate;
	}

	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

}
