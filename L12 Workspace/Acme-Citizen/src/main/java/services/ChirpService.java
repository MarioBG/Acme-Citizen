
package services;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ChirpRepository;
import domain.Chirp;

@Service
@Transactional
public class ChirpService {

	// Managed repository

	@Autowired
	private ChirpRepository			chirpRepository;

	// Supporting services

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors

	public ChirpService() {
		super();
	}

	// Simple CRUD methods

	public Chirp create() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		final Chirp res = new Chirp();

		res.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));
		res.setGovernmentAgent(this.governmentAgentService.findByPrincipal());

		return res;
	}

	public Collection<Chirp> findAll() {
		Collection<Chirp> res;
		res = this.chirpRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Chirp findOne(final int chirpId) {
		Assert.isTrue(chirpId != 0);
		Chirp res;
		res = this.chirpRepository.findOne(chirpId);
		Assert.notNull(res);
		return res;
	}

	public Chirp save(final Chirp chirp) {
		Assert.notNull(chirp);

		if (chirp.getId() == 0)
			chirp.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));

		final Chirp res = this.chirpRepository.save(chirp);

		if (chirp.getId() == 0)
			res.getGovernmentAgent().getChirps().add(res);

		return res;
	}

	public void delete(final Chirp chirp) {

		chirp.getGovernmentAgent().getChirps().remove(chirp);
		this.chirpRepository.delete(chirp);
	}

	// Other business methods

	public void flush() {
		this.chirpRepository.flush();
	}

	public Collection<Chirp> findByGovernmentAgentId(final int id) {
		return this.chirpRepository.findByGovernmentAgentId(id);
	}

}
