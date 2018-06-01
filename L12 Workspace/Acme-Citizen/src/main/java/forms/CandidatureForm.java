
package forms;

import javax.validation.constraints.Min;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.SafeHtml;
import org.hibernate.validator.constraints.SafeHtml.WhiteListType;
import org.hibernate.validator.constraints.URL;

public class CandidatureForm {

	private int		id;
	private int		electionId;
	private String	electoralProgram;
	private String	description;
	private String	partyLogo;
	private int		voteNumber;


	public CandidatureForm() {
		super();
	}

	public int getId() {
		return this.id;
	}

	public void setId(final int id) {
		this.id = id;
	}

	public int getElectionId() {
		return this.electionId;
	}

	public void setElectionId(final int electionId) {
		this.electionId = electionId;
	}

	@URL
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getElectoralProgram() {
		return this.electoralProgram;
	}

	public void setElectoralProgram(final String electoralProgram) {
		this.electoralProgram = electoralProgram;
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
	@NotBlank
	@SafeHtml(whitelistType = WhiteListType.NONE)
	public String getPartyLogo() {
		return this.partyLogo;
	}

	public void setPartyLogo(final String partyLogo) {
		this.partyLogo = partyLogo;
	}

	@Min(0)
	public int getVoteNumber() {
		return this.voteNumber;
	}

	public void setVoteNumber(final int voteNumber) {
		this.voteNumber = voteNumber;
	}

}
