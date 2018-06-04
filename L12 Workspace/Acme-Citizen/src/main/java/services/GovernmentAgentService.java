
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.GovernmentAgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Candidature;
import domain.Chirp;
import domain.Citizen;
import domain.Comment;
import domain.Election;
import domain.Folder;
import domain.GovernmentAgent;
import domain.Lottery;
import domain.Petition;
import forms.GovernmentAgentForm;

@Service
@Transactional
public class GovernmentAgentService {

	// Managed repository
	@Autowired
	private GovernmentAgentRepository	governmentAgentRepository;

	@Autowired
	private ActorService				actorService;

	@Autowired
	private Validator					validator;

	@Autowired
	private FolderService				folderService;

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private UserAccountService			userAccountService;


	// Constructors
	public GovernmentAgentService() {
		super();
	}

	// Simple CRUD methods

	public GovernmentAgent create() {
		final String nif = this.actorService.generateNif(this.findByPrincipal());
		final GovernmentAgent res = new GovernmentAgent();
		final Collection<Folder> folders = new ArrayList<Folder>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<Election> elections = new ArrayList<Election>();
		final Collection<Lottery> lotteries = new ArrayList<Lottery>();
		final Collection<Petition> petitions = new ArrayList<Petition>();
		final Collection<Chirp> chirps = new ArrayList<Chirp>();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();

		res.setNif(nif);
		res.setFolders(folders);
		res.setComments(comments);
		res.setChirps(chirps);
		res.setElections(elections);
		res.setLotteries(lotteries);
		res.setPetitions(petitions);
		authority.setAuthority(Authority.GOVERNMENTAGENT);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);

		return res;

	}

	public Collection<GovernmentAgent> findAll() {
		Collection<GovernmentAgent> res;
		res = this.governmentAgentRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public GovernmentAgent findOne(final int adminid) {
		Assert.isTrue(adminid != 0);
		GovernmentAgent res;
		res = this.governmentAgentRepository.findOne(adminid);
		Assert.notNull(res);

		return res;
	}

	public GovernmentAgent save(final GovernmentAgent admin) {
		GovernmentAgent res;

		if (admin.getId() == 0) {
			String pass = admin.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			admin.getUserAccount().setPassword(pass);

			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			admin.setFolders(folders);
			if (admin.getPhone().matches("^\\d+$"))
				admin.setPhone(this.configurationService.findActive().getDefaultCountryCode() + admin.getPhone());
			Assert.isTrue(this.userAccountService.findByUsername(admin.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		}

		res = this.governmentAgentRepository.save(admin);
		return res;
	}

	// Ancillary methods

	public GovernmentAgent findByPrincipal() {
		GovernmentAgent res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		if (userAccount == null)
			res = null;
		else
			res = this.governmentAgentRepository.findGovernmentAgentByUserAccountId(userAccount.getId());

		return res;
	}

	public boolean checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("GOVERNMENTAGENT");
		if (authority.contains(res))
			return true;
		else
			return false;
	}

	public GovernmentAgent reconstruct(final GovernmentAgentForm governmentAgentForm, final BindingResult binding) {

		Assert.notNull(governmentAgentForm);
		Assert.isTrue(governmentAgentForm.getTermsAndConditions() == true);

		GovernmentAgent res = new GovernmentAgent();

		if (governmentAgentForm.getId() != 0)
			res = this.findOne(governmentAgentForm.getId());
		else
			res = this.create();

		res.setName(governmentAgentForm.getName());
		res.setSurname(governmentAgentForm.getSurname());
		res.setEmail(governmentAgentForm.getEmail());
		res.setPhone(governmentAgentForm.getPhone());
		res.setAddress(governmentAgentForm.getAddress());
		res.setRegisterCode(governmentAgentForm.getRegisterCode());
		res.setCanCreateMoney(governmentAgentForm.getCanCreateMoney());
		res.getUserAccount().setUsername(governmentAgentForm.getUsername());
		res.getUserAccount().setPassword(governmentAgentForm.getPassword());

		if (binding != null)
			this.validator.validate(res, binding);

		return res;
	}
	public GovernmentAgentForm construct(final GovernmentAgent governmentAgent) {

		Assert.notNull(governmentAgent);
		final GovernmentAgentForm editGovernmentAgentForm = new GovernmentAgentForm();

		editGovernmentAgentForm.setId(governmentAgent.getId());
		editGovernmentAgentForm.setName(governmentAgent.getName());
		editGovernmentAgentForm.setSurname(governmentAgent.getSurname());
		editGovernmentAgentForm.setEmail(governmentAgent.getEmail());
		editGovernmentAgentForm.setPhone(governmentAgent.getPhone());
		editGovernmentAgentForm.setAddress(governmentAgent.getAddress());
		editGovernmentAgentForm.setRegisterCode(governmentAgent.getRegisterCode());
		editGovernmentAgentForm.setCanCreateMoney(governmentAgent.getCanCreateMoney());
		editGovernmentAgentForm.setUsername(governmentAgent.getUserAccount().getUsername());
		editGovernmentAgentForm.setPassword(governmentAgent.getUserAccount().getPassword());
		editGovernmentAgentForm.setRepeatPassword(governmentAgent.getUserAccount().getPassword());
		editGovernmentAgentForm.setTermsAndConditions(false);

		return editGovernmentAgentForm;
	}

	public void flush() {
		this.governmentAgentRepository.flush();
	}

	public Collection<Petition> getTop3PetitionsByCommentSize() {
		return this.governmentAgentRepository.getPetitionsByComments(new PageRequest(0, 3)).getContent();
	}

	public Double numberRegisteredActors() {
		return this.governmentAgentRepository.numberRegisteredActors();
	}

	public Double[] computeAvgMinMaxStdvPerCitizen() {
		return this.governmentAgentRepository.computeAvgMinMaxStdvPerCitizen();
	}
	public Double[] computeAvgMinMaxStdvPerGovAgent() {
		return this.governmentAgentRepository.computeAvgMinMaxStdvPerGovAgent();
	}
	public Collection<Petition> getPetitionsByComments() {
		return this.governmentAgentRepository.getPetitionsByComments(new PageRequest(0, 3)).getContent();
	}

	public Collection<Election> getElectionsByComments() {
		return this.governmentAgentRepository.getElectionsByComments(new PageRequest(0, 3)).getContent();
	}

	public Double getPercentageElectionCandidates() {
		return this.governmentAgentRepository.getPercentageElectionCandidates();
	}

	public Collection<Citizen> citizensMoreLotteryTicketsAverage() {
		return this.governmentAgentRepository.citizensMoreLotteryTicketsAverage();
	}

	public Collection<Candidature> candidaturesMoreVotesAverage() {
		return this.governmentAgentRepository.candidaturesMoreVotesAverage();
	}

	public Double[] computeAvgMinMaxStdvVotesPerElection() {
		return this.governmentAgentRepository.computeAvgMinMaxStdvVotesPerElection();
	}

	public Double[] computeAvgMinMaxStdvCandidaturesPerElection() {
		return this.governmentAgentRepository.computeAvgMinMaxStdvCandidaturesPerElection();
	}
	public Double getAllMoneyInSystem() {
		return this.governmentAgentRepository.getAllMoneyInSystem();
	}

	public Double[] computeAvgMinMaxStdvMoneyPerActor() {
		return this.governmentAgentRepository.computeAvgMinMaxStdvMoneyPerActor();
	}

}
