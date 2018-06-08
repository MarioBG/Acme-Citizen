
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PetitionRepository;
import domain.Citizen;
import domain.Comment;
import domain.GovernmentAgent;
import domain.Petition;
import forms.PetitionForm;

@Service
@Transactional
public class PetitionService {

	// Managed repository
	@Autowired
	private PetitionRepository		petitionRepository;

	// Supported services

	@Autowired
	private CitizenService			citizenService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private Validator				validator;


	// Constructors
	public PetitionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Petition create() {

		Assert.notNull(this.citizenService.findByPrincipal());

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

		Assert.isTrue(petitionId != 0);

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

		Assert.isTrue(petition.getName() != null && petition.getName() != "");
		Assert.isTrue(petition.getDescription() != null && petition.getDescription() != "");
		Assert.notNull(petition.getCreationMoment());

		if (petition.getId() == 0)
			petition.setCreationMoment(new Date(System.currentTimeMillis() - 1000));

		final Petition result = this.petitionRepository.save(petition);

		if (petition.getId() == 0)
			result.getCitizen().getPetitions().add(result);

		return result;
	}
	public void delete(final Petition petition) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		petition.getCitizen().getPetitions().remove(petition);

		final Collection<GovernmentAgent> governmentAgents = new ArrayList<GovernmentAgent>(petition.getGovernmentAgents());
		final Collection<Comment> comments = new ArrayList<Comment>(petition.getComments());
		for (final GovernmentAgent g : governmentAgents)
			g.getPetitions().remove(petition);
		for (final Comment c : comments)
			this.commentService.delete(c);

		this.petitionRepository.delete(petition);
	}

	public void virtualDelete(final Petition petition) {

		Assert.notNull(petition);
		Assert.isTrue(petition.getId() != 0);
		Assert.notNull(this.citizenService.findByPrincipal());
		Assert.isTrue(petition.getFinalVersion() == false);

		petition.setDeleted(true);
		this.save(petition);
	}

	public void resolve(final Petition petition) {

		Assert.notNull(petition);
		Assert.isTrue(petition.getId() != 0);
		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Assert.isTrue(petition.getResolved() == false);

		petition.setResolved(true);
		this.save(petition);
	}

	// Ancillary methods

	public PetitionForm construct(final Petition petition) {

		Assert.notNull(petition);

		PetitionForm petitionForm;

		petitionForm = new PetitionForm();

		petitionForm.setId(petition.getId());
		petitionForm.setCitizenId(petition.getCitizen().getId());
		petitionForm.setName(petition.getName());
		petitionForm.setDescription(petition.getDescription());
		petitionForm.setPicture(petition.getPicture());
		petitionForm.setCreationMoment(petition.getCreationMoment());
		petitionForm.setFinalVersion(petition.getFinalVersion());
		petitionForm.setResolved(petition.getResolved());
		petitionForm.setDeleted(petition.getDeleted());

		return petitionForm;
	}

	public Petition reconstruct(final PetitionForm petitionForm, final BindingResult binding) {

		Assert.notNull(petitionForm);

		Petition petition;

		if (petitionForm.getId() != 0)
			petition = this.findOne(petitionForm.getId());
		else
			petition = this.create();

		petition.setName(petitionForm.getName());
		petition.setDescription(petitionForm.getDescription());
		petition.setPicture(petitionForm.getPicture());
		petition.setCreationMoment(petitionForm.getCreationMoment());
		petition.setFinalVersion(petitionForm.getFinalVersion());
		petition.setResolved(petitionForm.getResolved());
		petition.setDeleted(petitionForm.getDeleted());

		if (binding != null)
			this.validator.validate(petition, binding);

		return petition;
	}

	public void addGovernmentAgent(final int petitionId, final int governmentAgentId) {

		final Petition petition = this.findOne(petitionId);
		Assert.isTrue(petition.getFinalVersion() == false);
		Assert.isTrue(petition.getCitizen() == this.citizenService.findByPrincipal());
		final GovernmentAgent governmentAgent = this.governmentAgentService.findOne(governmentAgentId);
		Assert.isTrue(governmentAgent != null && !petition.getGovernmentAgents().contains(governmentAgent));
		petition.getGovernmentAgents().add(governmentAgent);
		governmentAgent.getPetitions().add(petition);
		this.save(petition);
		this.governmentAgentService.save(governmentAgent);
	}

	public void removeGovernmentAgent(final int petitionId, final int governmentAgentId) {

		final Petition petition = this.findOne(petitionId);
		Assert.isTrue(petition.getFinalVersion() == false);
		Assert.isTrue(petition.getCitizen() == this.citizenService.findByPrincipal());
		final GovernmentAgent governmentAgent = this.governmentAgentService.findOne(governmentAgentId);
		Assert.isTrue(governmentAgent != null && petition.getGovernmentAgents().contains(governmentAgent));
		petition.getGovernmentAgents().remove(governmentAgent);
		governmentAgent.getPetitions().remove(petition);
		this.save(petition);
		this.governmentAgentService.save(governmentAgent);
	}

	public Collection<Petition> findNonDeleted() {

		final Collection<Petition> result = this.petitionRepository.findNonDeleted();
		return result;
	}

	public Collection<Petition> findDeleted() {

		final Collection<Petition> result = this.petitionRepository.findDeleted();
		return result;
	}

	public Collection<Petition> findByCitizenIdNonDeleted(final int citizenId) {

		Assert.isTrue(citizenId != 0);

		final Collection<Petition> result = this.petitionRepository.findByCitizenIdNonDeleted(citizenId);
		return result;
	}

	public Collection<Petition> findByCitizenId(final int citizenId) {

		Assert.isTrue(citizenId != 0);
		Assert.notNull(this.citizenService.findOne(citizenId));

		final Collection<Petition> result = this.petitionRepository.findByCitizenId(citizenId);
		return result;
	}

	public Collection<Petition> findByPrincipal() {

		final Citizen citizen = this.citizenService.findByPrincipal();
		Assert.notNull(citizen);
		final Collection<Petition> result = this.findByCitizenIdNonDeleted(citizen.getId());
		return result;
	}

	public void flush() {
		this.petitionRepository.flush();
	}

}
