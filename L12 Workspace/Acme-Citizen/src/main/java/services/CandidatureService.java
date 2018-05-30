
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.CandidatureRepository;
import domain.Candidate;
import domain.Candidature;
import domain.Comment;

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

		if (candidature.getId() == 0)
			result.getElection().getCandidatures().add(result);

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
