
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CandidatureRepository;
import domain.Candidate;
import domain.Candidature;
import domain.Citizen;
import domain.Comment;
import domain.Election;
import forms.CandidatureForm;

@Service
@Transactional
public class CandidatureService {

	// Managed repository
	@Autowired
	private CandidatureRepository	candidatureRepository;

	// Supported services

	@Autowired
	private CandidateService		candidateService;

	@Autowired
	private ElectionService			electionService;

	@Autowired
	private CommentService			commentService;

	@Autowired
	private CitizenService			citizenService;

	@Autowired
	private Validator				validator;


	// Constructors
	public CandidatureService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Candidature create(final int electionId) {

		final Candidature candidature = new Candidature();

		candidature.setVoteNumber(0);

		candidature.setElection(this.electionService.findOne(electionId));
		candidature.setCandidates(new ArrayList<Candidate>());
		candidature.setComments(new ArrayList<Comment>());

		return candidature;
	}

	public Candidature findOne(final int candidatureId) {

		Candidature result = null;
		result = this.candidatureRepository.findOne(candidatureId);
		return result;
	}

	public Collection<Candidature> findAll() {

		Collection<Candidature> result = null;
		result = this.candidatureRepository.findAll();
		return result;
	}

	public Candidature save(final Candidature candidature) {

		final Candidature result = this.candidatureRepository.save(candidature);

		if (candidature.getId() == 0) {
			result.getElection().getCandidatures().add(result);
			final Candidate candidate = this.candidateService.create(result.getId());
			this.candidateService.save(candidate);
		}

		return result;
	}

	public void delete(final Candidature candidature) {

		candidature.getElection().getCandidatures().remove(candidature);

		final Collection<Candidate> candidates = new ArrayList<Candidate>(candidature.getCandidates());
		final Collection<Comment> comments = new ArrayList<Comment>(candidature.getComments());
		for (final Candidate c : candidates)
			this.candidateService.delete(c);
		for (final Comment c : comments)
			this.commentService.delete(c);

		this.candidatureRepository.delete(candidature);
	}

	// Ancillary methods

	public void vote(final Candidature candidature) {

		final Citizen principal = this.citizenService.findByPrincipal();
		Assert.notNull(principal);
		final Election election = candidature.getElection();
		Assert.isTrue(!principal.getElections().contains(election));
		candidature.setVoteNumber(candidature.getVoteNumber() + 1);
		principal.getElections().add(election);
		election.getCitizens().add(principal);
		this.save(candidature);
	}
	public CandidatureForm construct(final Candidature candidature) {

		Assert.notNull(candidature);

		CandidatureForm candidatureForm;

		candidatureForm = new CandidatureForm();

		candidatureForm.setId(candidature.getId());
		candidatureForm.setElectionId(candidature.getElection().getId());
		candidatureForm.setElectoralProgram(candidature.getElectoralProgram());
		candidatureForm.setDescription(candidature.getDescription());
		candidatureForm.setPartyLogo(candidature.getPartyLogo());
		candidatureForm.setVoteNumber(candidature.getVoteNumber());

		return candidatureForm;
	}

	public Candidature reconstruct(final CandidatureForm candidatureForm, final BindingResult binding) {

		Assert.notNull(candidatureForm);

		Candidature candidature;

		if (candidatureForm.getId() != 0)
			candidature = this.findOne(candidatureForm.getId());
		else
			candidature = this.create(candidatureForm.getElectionId());

		candidature.setElectoralProgram(candidatureForm.getElectoralProgram());
		candidature.setDescription(candidatureForm.getDescription());
		candidature.setPartyLogo(candidatureForm.getPartyLogo());
		candidature.setVoteNumber(candidatureForm.getVoteNumber());

		if (binding != null)
			this.validator.validate(candidature, binding);

		return candidature;
	}

	public Collection<Candidature> findByCitizenId(final int citizenId) {

		Assert.isTrue(citizenId != 0);

		final Collection<Candidature> candidatures = this.candidatureRepository.findByCitizenId(citizenId);
		return candidatures;
	}

	public Collection<Candidature> findByElectionId(final int electionId) {

		Assert.isTrue(electionId != 0);

		final Collection<Candidature> candidatures = this.candidatureRepository.findByElectionId(electionId);
		return candidatures;
	}

}
