
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.CitizenRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import security.UserAccountService;
import domain.Candidate;
import domain.Citizen;
import domain.Comment;
import domain.Election;
import domain.Folder;
import domain.LotteryTicket;
import domain.Petition;
import forms.CitizenForm;

@Service
@Transactional
public class CitizenService {

	// Managed repository
	@Autowired
	private CitizenRepository		citizenRepository;

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private Validator				validator;

	@Autowired
	private FolderService			folderService;

	@Autowired
	private UserAccountService		userAccountService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors
	public CitizenService() {
		super();
	}

	// Simple CRUD methods

	public Citizen create() {
		final Citizen res = new Citizen();
		final String nif = this.actorService.generateNif(this.governmentAgentService.findByPrincipal());
		final Collection<Folder> folders = new ArrayList<Folder>();
		final Collection<Candidate> candidates = new ArrayList<Candidate>();
		final Collection<Comment> comments = new ArrayList<Comment>();
		final Collection<Election> elections = new ArrayList<Election>();
		final Collection<LotteryTicket> lotteryTickets = new ArrayList<LotteryTicket>();
		final Collection<Petition> petitions = new ArrayList<Petition>();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();

		res.setCandidates(candidates);
		res.setComments(comments);
		res.setElections(elections);
		res.setFolders(folders);
		res.setLotteryTickets(lotteryTickets);
		res.setPetitions(petitions);
		res.setNif(nif);
		authority.setAuthority(Authority.CITIZEN);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);

		return res;

	}
	public Collection<Citizen> findAll() {
		Collection<Citizen> res;
		res = this.citizenRepository.findAll();
		Assert.notNull(res);

		return res;
	}

	public Citizen findOne(final int adminid) {
		Assert.isTrue(adminid != 0);
		Citizen res;
		res = this.citizenRepository.findOne(adminid);
		Assert.notNull(res);

		return res;
	}

	public Citizen save(final Citizen citizen) {
		Citizen res;

		if (citizen.getId() == 0) {
			String pass = citizen.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			citizen.setFolders(folders);

			citizen.getUserAccount().setPassword(pass);

			if (citizen.getPhone().matches("^\\d+$"))
				citizen.setPhone(this.configurationService.findActive().getDefaultCountryCode() + citizen.getPhone());
			Assert.isTrue(this.userAccountService.findByUsername(citizen.getUserAccount().getUsername()) == null, "message.error.duplicatedUser");
		}

		res = this.citizenRepository.save(citizen);
		return res;
	}

	// Ancillary methods

	public Citizen findByPrincipal() {
		Citizen res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();

		if (userAccount == null)
			res = null;
		else
			res = this.citizenRepository.findCitizenByUserAccountId(userAccount.getId());

		return res;
	}

	public boolean checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("CITIZEN");
		if (authority.contains(res))
			return true;
		else
			return false;
	}

	public void flush() {
		this.citizenRepository.flush();
	}

	public Citizen reconstruct(final CitizenForm citizenForm, final BindingResult binding) {

		Assert.notNull(citizenForm);
		Assert.isTrue(citizenForm.getTermsAndConditions() == true);

		Citizen res = new Citizen();

		if (citizenForm.getId() != 0)
			res = this.findOne(citizenForm.getId());
		else
			res = this.create();

		res.setName(citizenForm.getName());
		res.setSurname(citizenForm.getSurname());
		res.setEmail(citizenForm.getEmail());
		res.setPhone(citizenForm.getPhone());
		res.setAddress(citizenForm.getAddress());
		res.setNif(citizenForm.getNif());
		res.setNickname(citizenForm.getNickname());
		res.getUserAccount().setUsername(citizenForm.getUsername());
		res.getUserAccount().setPassword(citizenForm.getPassword());

		this.validator.validate(res, binding);

		return res;
	}

	public CitizenForm construct(final Citizen citizen) {

		Assert.notNull(citizen);
		final CitizenForm editCitizenForm = new CitizenForm();

		editCitizenForm.setId(citizen.getId());
		editCitizenForm.setName(citizen.getName());
		editCitizenForm.setSurname(citizen.getSurname());
		editCitizenForm.setEmail(citizen.getEmail());
		editCitizenForm.setPhone(citizen.getPhone());
		editCitizenForm.setAddress(citizen.getAddress());
		editCitizenForm.setNif(citizen.getNif());
		editCitizenForm.setNickname(citizen.getNickname());
		editCitizenForm.setUsername(citizen.getUserAccount().getUsername());
		editCitizenForm.setPassword(citizen.getUserAccount().getPassword());
		editCitizenForm.setRepeatPassword(citizen.getUserAccount().getPassword());
		editCitizenForm.setTermsAndConditions(false);

		return editCitizenForm;
	}

	//	public boolean checkIsSpam(String text) {
	//		Collection<String> spamWords;
	//		Boolean isSpam = false;
	//		Actor a = this.actorService.findByPrincipal();
	//		String type = this.actorService.getType(a.getUserAccount());
	//
	//		if (type.equals("CUSTOMER")) {
	//			a = (Customer) a;
	//		} else if (type.equals("USER")) {
	//			a = (User) a;
	//		} else if (type.equals("AGENT")) {
	//			a = (Agent) a;
	//		}
	//
	//		if (text == null) {
	//			return isSpam;
	//		} else {
	//			text = text.toLowerCase();
	//			spamWords = configurationService.getTabooWordsFromConfiguration();
	//			for (String spamword : spamWords) {
	//				if (text.contains(spamword)) {
	//					isSpam = true;
	//				}
	//			}
	//		}
	//		return isSpam;
	//	}

	//	// C-1
	//	public Object[] avgSqtrUser() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Object[] res;
	//		res = this.governmentAgentRepository.avgSqtrUser();
	//		return res;
	//	}
	//
	//	// C-2
	//	public Object[] avgSqtrArticlesByWriter() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Object[] res;
	//		res = this.governmentAgentRepository.avgSqtrArticlesByWriter();
	//		return res;
	//	}
	//
	//	// C-3
	//	public Object[] avgSqtrArticlesByNewspaper() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Object[] res;
	//		res = this.governmentAgentRepository.avgSqtrArticlesByNewspaper();
	//		return res;
	//	}
	//
	//	// C-4
	//	public Collection<Newspaper> newspapersMoreAverage() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Collection<Newspaper> res = this.governmentAgentRepository.newspapersMoreAverage();
	//		return res;
	//	}
	//
	//	// C-5
	//	public Collection<Newspaper> newspapersFewerAverage() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Collection<Newspaper> res = this.governmentAgentRepository.newspapersFewerAverage();
	//		return res;
	//	}
	//
	//	// C-6
	//	public String ratioUserCreatedNewspaper() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		String res;
	//		res = this.governmentAgentRepository.ratioUserCreatedNewspaper();
	//		return res;
	//	}
	//
	//	// C-7
	//	public String ratioUserWrittenArticle() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		String res;
	//		res = this.governmentAgentRepository.ratioUserWrittenArticle();
	//		return res;
	//	}
	//
	//	// B-1
	//	public Double avgFollowupsPerArticle() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Double res;
	//		res = this.governmentAgentRepository.avgFollowupsPerArticle();
	//		return res;
	//	}
	//
	//	// B-2
	//	public Double avgNumberOfFollowUpsPerArticleAfter1Week() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Double res;
	//		long oneWeek = TimeUnit.DAYS.toMillis(7);
	//		Date f = new Date(System.currentTimeMillis() - oneWeek);
	//		res = this.governmentAgentRepository.avgNumberOfFollowUpsPerArticleAfter1Week(f);
	//		return res;
	//	}
	//
	//	// B-3
	//	public Double avgNumberOfFollowUpsPerArticleAfter2Week() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Double res;
	//		long twoWeeks = TimeUnit.DAYS.toMillis(7);
	//		Date f = new Date(System.currentTimeMillis() - twoWeeks);
	//		res = this.governmentAgentRepository.avgNumberOfFollowUpsPerArticleAfter2Week(f);
	//		return res;
	//	}
	//
	//	// B-4
	//	public Object[] avgStddevNumberOfChirpPerUser() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		Object[] res;
	//		res = this.governmentAgentRepository.avgStddevNumberOfChirpPerUser();
	//		return res;
	//	}
	//
	//	// B-5
	//	public String ratioUsersMorePostedChirpsOfAveragePerUser() {
	//
	//		Assert.isTrue(this.checkAuthority());
	//
	//		String res;
	//		res = this.governmentAgentRepository.ratioUsersMorePostedChirpsOfAveragePerUser();
	//		return res;
	//	}
	//
	//	// ACME-NEWSPAPER 2.0
	//
	//	// C-1
	//	public Double ratioNewspapersAds() {
	//		return this.governmentAgentRepository.ratioNewspapersWithVsWithoutAdvertisements();
	//	}
	//
	//	// C-2
	//	public Double ratioAdsTabooWords() {
	//		return (double) this.advertisementService.getAdvertisementsTabooWords().size() / (double) this.advertisementService.findAll().size();
	//	}
	//
	//	// B-1
	//	public Double avgNumberOfNewspapersPerVolume() {
	//
	//		Double result = this.governmentAgentRepository.avgNumberOfNewspapersPerVolume();
	//		return result;
	//	}
	//
	//	// B-2
	//
	//	public String ratioSubscriptionsVolumeVersusSubscriptionsNewspaper() {
	//
	//		String result = this.governmentAgentRepository.ratioSubscriptionsVolumeVersusSubscriptionsNewspaper();
	//		return result;
	//	}

}
