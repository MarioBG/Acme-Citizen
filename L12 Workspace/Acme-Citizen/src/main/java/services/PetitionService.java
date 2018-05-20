
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.PetitionRepository;
import domain.Comment;
import domain.GovernmentAgent;
import domain.Petition;

@Service
@Transactional
public class PetitionService {

	// Managed repository
	@Autowired
	private PetitionRepository	petitionRepository;

	// Supported services

	@Autowired
	private CitizenService		citizenService;

	@Autowired
	private CommentService		commentService;


	// Constructors
	public PetitionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Petition create() {

		final Petition petition = new Petition();

		petition.setCreationMoment(new Date(System.currentTimeMillis() - 1000));
		petition.setFinalVersion(false);
		petition.setResolved(false);
		petition.setDeleted(false);

		petition.setCitizen(this.citizenService.findByPrincipal());
		petition.setGovernmentAgents(new ArrayList<GovernmentAgent>());
		petition.setComments(new ArrayList<Comment>());

		return petition;
	}

	public Petition findOne(final int petitionId) {

		Petition result = null;
		result = this.petitionRepository.findOne(petitionId);
		return result;
	}

	public Collection<Petition> findAll() {

		Collection<Petition> result = null;
		result = this.petitionRepository.findAll();
		return result;
	}

	public Petition save(final Petition petition) {

		if (petition.getId() == 0)
			petition.setCreationMoment(new Date(System.currentTimeMillis() - 1000));

		final Petition result = this.petitionRepository.save(petition);

		if (petition.getId() == 0)
			result.getCitizen().getPetitions().add(result);

		return result;
	}
	public void delete(final Petition petition) {

		petition.getCitizen().getPetitions().remove(petition);

		final Collection<GovernmentAgent> governmentAgents = new ArrayList<GovernmentAgent>(petition.getGovernmentAgents());
		final Collection<Comment> comments = new ArrayList<Comment>(petition.getComments());
		for (final GovernmentAgent g : governmentAgents)
			g.getPetitions().remove(petition);
		for (final Comment c : comments)
			this.commentService.delete(c);

		this.petitionRepository.delete(petition);
	}

	// Ancillary methods

}
