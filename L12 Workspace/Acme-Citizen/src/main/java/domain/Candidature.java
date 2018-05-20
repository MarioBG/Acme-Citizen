
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
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
public class Candidature extends Commentable {

	// Constructor ----------------------------------------------------------------------------
	public Candidature() {
		super();
	}


	// Attributes ------------------------------------------------------------------------------

	private String	electoralProgram;
	private String	description;
	private String	partyLogo;
	private int		voteNumber;


	@NotBlank
	@URL
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

	@NotBlank
	@URL
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


	// Relashionships ----------------------------------------------------------------

	private Collection<Candidate>	candidates;
	private Election				election;


	@Valid
	@NotNull
	@OneToMany(mappedBy = "candidature")
	public Collection<Candidate> getCandidates() {
		return this.candidates;
	}

	public void setCandidates(final Collection<Candidate> candidates) {
		this.candidates = candidates;
	}

	@Valid
	@ManyToOne(optional = false)
	public Election getElection() {
		return this.election;
	}

	public void setElection(final Election election) {
		this.election = election;
	}

}
