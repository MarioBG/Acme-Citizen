
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

import repositories.BankAgentRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Actor;
import domain.BankAgent;
import domain.Folder;
import forms.AgentForm;

@Service
@Transactional
public class BankAgentService {

	// Managed repository

	@Autowired
	private BankAgentRepository	bankAgentRepository;

	// Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private FolderService		folderService;

	@Autowired
	private Validator			validator;


	// Constructors

	public BankAgentService() {
		super();
	}

	// Simple CRUD methods

	public BankAgent create() {

		final Actor principal = this.actorService.findByPrincipal();
		Assert.isTrue(principal == null);

		final BankAgent res = new BankAgent();

		final UserAccount agentAccount = new UserAccount();
		final Authority authority = new Authority();
		Collection<Folder> folders = new ArrayList<Folder>();
		authority.setAuthority(Authority.BANKAGENT);
		agentAccount.addAuthority(authority);
		res.setUserAccount(agentAccount);

		res.setFolders(folders);

		return res;
	}

	public Collection<BankAgent> findAll() {
		Collection<BankAgent> res;
		res = this.bankAgentRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public BankAgent findOne(final int agentId) {
		Assert.isTrue(agentId != 0);
		BankAgent res;
		res = this.bankAgentRepository.findOne(agentId);
		return res;
	}

	public BankAgent save(final BankAgent agent) {
		BankAgent res;
		if (agent.getId() == 0) {
			final Collection<Folder> folders = this.folderService.save(this.folderService.defaultFolders());
			agent.setFolders(folders);
			agent.getUserAccount().setPassword(new Md5PasswordEncoder().encodePassword(agent.getUserAccount().getPassword(), null));
		}
		res = this.bankAgentRepository.save(agent);

		return res;
	}

	// Other business methods

	public BankAgent findBankAgentByUserAccountId(final int uA) {
		return this.bankAgentRepository.findBankAgentByUserAccountId(uA);
	}

	public BankAgent findByPrincipal() {
		BankAgent res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.bankAgentRepository.findBankAgentByUserAccountId(userAccount.getId());
		return res;
	}

	public BankAgent reconstruct(final AgentForm agentForm, final BindingResult binding) {

		Assert.notNull(agentForm);
		Assert.isTrue(agentForm.getTermsAndConditions() == true);

		BankAgent res = new BankAgent();

		if (agentForm.getId() != 0)
			res = this.findOne(agentForm.getId());
		else
			res = this.create();

		res.setName(agentForm.getName());
		res.setSurname(agentForm.getSurname());
		res.setEmail(agentForm.getEmail());
		res.setPhone(agentForm.getPhone());
		res.setAddress(agentForm.getAddress());
		res.getUserAccount().setUsername(agentForm.getUsername());
		res.getUserAccount().setPassword(agentForm.getPassword());

		if (binding != null) {
			this.validator.validate(res, binding);
		}

		return res;
	}

	public AgentForm construct(final BankAgent agent) {

		Assert.notNull(agent);
		final AgentForm editAgentForm = new AgentForm();

		editAgentForm.setId(agent.getId());
		editAgentForm.setName(agent.getName());
		editAgentForm.setSurname(agent.getSurname());
		editAgentForm.setEmail(agent.getEmail());
		editAgentForm.setPhone(agent.getPhone());
		editAgentForm.setAddress(agent.getAddress());
		editAgentForm.setUsername(agent.getUserAccount().getUsername());
		editAgentForm.setPassword(agent.getUserAccount().getPassword());
		editAgentForm.setRepeatPassword(agent.getUserAccount().getPassword());
		editAgentForm.setTermsAndConditions(false);

		return editAgentForm;
	}

	public void flush() {
		this.bankAgentRepository.flush();
	}

}
