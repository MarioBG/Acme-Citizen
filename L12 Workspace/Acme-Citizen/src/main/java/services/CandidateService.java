
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.CandidateRepository;
import domain.Candidate;
import domain.Citizen;

@Service
@Transactional
public class CandidateService {

	// Managed repository
	@Autowired
	private CandidateRepository	candidateRepository;

	// Supported services

	@Autowired
	private CitizenService		citizenService;

	@Autowired
	private CandidatureService	candidatureService;


	// Constructors
	public CandidateService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Candidate create(final int candidatureId) {

		final Candidate candidate = new Candidate();

		candidate.setListOrder(0);

		candidate.setCitizen(this.citizenService.findByPrincipal());
		candidate.setCandidature(this.candidatureService.findOne(candidatureId));

		return candidate;
	}

	public Candidate findOne(final int candidateId) {

		Candidate result = null;
		result = this.candidateRepository.findOne(candidateId);
		return result;
	}

	public Collection<Candidate> findAll() {

		Collection<Candidate> result = null;
		result = this.candidateRepository.findAll();
		return result;
	}

	public Candidate save(final Candidate candidate) {

		final Candidate result = this.candidateRepository.save(candidate);

		if (candidate.getId() == 0) {
			result.getCandidature().getCandidates().add(result);
			result.getCitizen().getCandidates().add(result);
		}

		return result;
	}

	public void delete(final Candidate candidate) {

		candidate.getCitizen().getCandidates().remove(candidate);
		candidate.getCandidature().getCandidates().remove(candidate);

		this.candidateRepository.delete(candidate);
	}

	// Ancillary methods

	public Candidate findByCitizenIdAndCandidatureId(final int citizenId, final int candidatureId) {

		final Candidate candidate = this.candidateRepository.findByCitizenIdAndCandidatureId(citizenId, candidatureId);
		return candidate;
	}

	public Candidate findByPrincipalAndCandidatureId(final int candidatureId) {

		final Citizen citizen = this.citizenService.findByPrincipal();
		final Candidate candidate = this.findByCitizenIdAndCandidatureId(citizen.getId(), candidatureId);
		return candidate;
	}
}
