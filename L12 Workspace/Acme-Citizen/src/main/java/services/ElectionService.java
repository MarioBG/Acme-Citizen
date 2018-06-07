
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ElectionRepository;
import domain.Candidature;
import domain.Citizen;
import domain.Comment;
import domain.Election;
import forms.ElectionForm;

@Service
@Transactional
public class ElectionService {

	// Managed repository
	@Autowired
	private ElectionRepository		electionRepository;

	// Supported services

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private CandidatureService		candidatureService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private Validator				validator;


	// Constructors
	public ElectionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Election create() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Assert.isTrue(this.governmentAgentService.findByPrincipal().getCanOrganiseElection() == true);

		final Election election = new Election();

		election.setGovernmentAgent(this.governmentAgentService.findByPrincipal());
		election.setCandidatures(new ArrayList<Candidature>());
		election.setCitizens(new ArrayList<Citizen>());
		election.setComments(new ArrayList<Comment>());

		return election;
	}

	public Election findOne(final int electionId) {

		Election result = null;
		result = this.electionRepository.findOne(electionId);
		return result;
	}

	public Collection<Election> findAll() {

		Collection<Election> result = null;
		result = this.electionRepository.findAll();
		return result;
	}

	public Election save(final Election election) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Assert.isTrue(this.governmentAgentService.findByPrincipal().getCanOrganiseElection() == true);
		Assert.isTrue(election.getCandidatureDate().before(election.getCelebrationDate()));

		final Election result = this.electionRepository.save(election);

		if (election.getId() == 0)
			result.getGovernmentAgent().getElections().add(result);

		return result;
	}

	public void delete(final Election election) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		election.getGovernmentAgent().getElections().remove(election);

		final Collection<Candidature> candidatures = new ArrayList<Candidature>(election.getCandidatures());
		final Collection<Citizen> citizens = new ArrayList<Citizen>(election.getCitizens());
		final Collection<Comment> comments = new ArrayList<Comment>(election.getComments());
		for (final Candidature c : candidatures)
			this.candidatureService.delete(c);
		for (final Citizen c : citizens)
			c.getElections().remove(election);
		for (final Comment c : comments)
			this.commentService.delete(c);

		this.electionRepository.delete(election);
	}

	// Ancillary methods

	public ElectionForm construct(final Election election) {

		Assert.notNull(election);

		ElectionForm electionForm;

		electionForm = new ElectionForm();

		electionForm.setId(election.getId());
		electionForm.setGovernmentAgentId(election.getGovernmentAgent().getId());
		electionForm.setCandidatureDate(election.getCandidatureDate());
		electionForm.setCelebrationDate(election.getCelebrationDate());
		electionForm.setDescription(election.getDescription());

		return electionForm;
	}

	public Election reconstruct(final ElectionForm electionForm, final BindingResult binding) {

		Assert.notNull(electionForm);

		Election election;

		if (electionForm.getId() != 0)
			election = this.findOne(electionForm.getId());
		else
			election = this.create();

		election.setCandidatureDate(electionForm.getCandidatureDate());
		election.setCelebrationDate(electionForm.getCelebrationDate());
		election.setDescription(electionForm.getDescription());
		election.setGovernmentAgent(this.governmentAgentService.findOne(electionForm.getGovernmentAgentId()));

		if (binding != null)
			this.validator.validate(election, binding);

		return election;
	}

}
