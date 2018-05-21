
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ElectionRepository;
import domain.Candidature;
import domain.Citizen;
import domain.Comment;
import domain.Election;

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


	// Constructors
	public ElectionService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Election create() {

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

		Assert.isTrue(election.getCandidatureDate().before(election.getCelebrationDate()));

		final Election result = this.electionRepository.save(election);

		if (election.getId() == 0)
			result.getGovernmentAgent().getElections().add(result);

		return result;
	}

	public void delete(final Election election) {

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

}
