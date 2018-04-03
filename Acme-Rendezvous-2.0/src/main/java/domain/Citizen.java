
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Citizen extends Actor {

	// Constructors

	public Citizen() {
		super();
	}


	// Relationships

	//private Collection<Answer>		answer;
	private Collection<Petition>	petitions;


	//	@Valid
	//	@OneToMany(mappedBy = "user")
	//	public Collection<Answer> getAnswer() {
	//		return this.answer;
	//	}
	//
	//	public void setAnswer(final Collection<Answer> answer) {
	//		this.answer = answer;
	//	}

	@Valid
	@OneToMany(mappedBy = "citizen")
	public Collection<Petition> getPetitions() {
		return this.petitions;
	}

	public void setPetitions(final Collection<Petition> petitions) {
		this.petitions = petitions;
	}

}
