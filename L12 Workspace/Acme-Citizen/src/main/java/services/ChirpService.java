
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ChirpRepository;
import domain.Chirp;
import forms.ChirpForm;

@Service
@Transactional
public class ChirpService {

	// Managed repository

	@Autowired
	private ChirpRepository			chirpRepository;

	// Supporting services

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private Validator				validator;


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
		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Assert.isTrue(chirp.getTitle() != "" && chirp.getTitle() != null);
		Assert.isTrue(chirp.getContent() != "" && chirp.getContent() != null);

		if (chirp.getId() == 0)
			chirp.setPublicationMoment(new Date(System.currentTimeMillis() - 1000));

		final Chirp res = this.chirpRepository.save(chirp);

		if (chirp.getId() == 0)
			res.getGovernmentAgent().getChirps().add(res);

		return res;
	}
	public void delete(final Chirp chirp) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		chirp.getGovernmentAgent().getChirps().remove(chirp);
		this.chirpRepository.delete(chirp);
	}

	// Other business methods

	public ArrayList<Chirp> lastChirps() {

		ArrayList<Chirp> chirps1, chirps2;

		chirps1 = (ArrayList<Chirp>) this.findAll();
		Collections.sort(chirps1);
		if (chirps1.size() > 3)
			chirps2 = new ArrayList<Chirp>(chirps1.subList(0, 3));
		else
			chirps2 = new ArrayList<Chirp>(chirps1);

		return chirps2;
	}

	public ChirpForm construct(final Chirp chirp) {

		Assert.notNull(chirp);

		ChirpForm chirpForm;

		chirpForm = new ChirpForm();

		chirpForm.setId(chirp.getId());
		chirpForm.setGovernmentAgentId(chirp.getGovernmentAgent().getId());
		chirpForm.setTitle(chirp.getTitle());
		chirpForm.setContent(chirp.getContent());
		chirpForm.setPublicationMoment(chirp.getPublicationMoment());
		chirpForm.setImage(chirp.getImage());
		chirpForm.setLink(chirp.getLink());

		return chirpForm;
	}

	public Chirp reconstruct(final ChirpForm chirpForm, final BindingResult binding) {

		Assert.notNull(chirpForm);

		Chirp chirp;

		if (chirpForm.getId() != 0)
			chirp = this.findOne(chirpForm.getId());
		else
			chirp = this.create();

		chirp.setTitle(chirpForm.getTitle());
		chirp.setContent(chirpForm.getContent());
		chirp.setPublicationMoment(chirpForm.getPublicationMoment());
		chirp.setImage(chirpForm.getImage());
		chirp.setLink(chirpForm.getLink());

		if (binding != null)
			this.validator.validate(chirp, binding);

		return chirp;
	}

	public void flush() {
		this.chirpRepository.flush();
	}

	public Collection<Chirp> findByGovernmentAgentId(final int id) {
		return this.chirpRepository.findByGovernmentAgentId(id);
	}

	public Collection<Chirp> findByPrincipal() {
		return this.findByGovernmentAgentId(this.governmentAgentService.findByPrincipal().getId());
	}

}
