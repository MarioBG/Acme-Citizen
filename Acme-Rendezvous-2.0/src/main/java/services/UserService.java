
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.UserRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Petition;
import domain.Citizen;
import forms.UserForm;

@Service
@Transactional
public class UserService {

	// Managed repository

	@Autowired
	private UserRepository	userRepository;

	// Supporting services

	@Autowired
	private Validator		validator;


	// Constructors

	public UserService() {
		super();
	}

	// Simple CRUD methods

	public Citizen create() {
		final Citizen res = new Citizen();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();
		//final Collection<Answer> answer = new ArrayList<Answer>();
		final Collection<Petition> organisedrendezvouses = new ArrayList<Petition>();
		final Collection<Petition> rsvpdRendezvouses = new ArrayList<Petition>();

		authority.setAuthority(Authority.USER);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);
		//res.setAnswer(answer);
		res.setOrganisedRendezvouses(organisedrendezvouses);
		res.setRsvpdRendezvouses(rsvpdRendezvouses);

		return res;
	}

	public Collection<Citizen> findAll() {
		Collection<Citizen> res;
		res = this.userRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Citizen findOne(final int userId) {
		Assert.isTrue(userId != 0);
		Citizen res;
		res = this.userRepository.findOne(userId);
		Assert.notNull(res);
		return res;
	}

	public Citizen save(final Citizen user) {
		Citizen res;

		if (user.getId() == 0) {
			String pass = user.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			user.getUserAccount().setPassword(pass);
		}
		res = this.userRepository.save(user);
		return res;
	}

	// public void delete(User user) {
	// Assert.notNull(user);
	// Assert.isTrue(user.getId() != 0);
	// Assert.isTrue(this.userRepository.exists(user.getId()));
	// this.actorRepository.delete(user);
	// }

	// Other business methods

	public Citizen findByPrincipal() {
		Citizen res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		if (userAccount == null)
			res = null;
		else
			res = this.userRepository.findUserByUserAccountId(userAccount.getId());
		return res;
	}

	public boolean checkAuthority() {
		boolean result = false;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("USER");
		if(authority.contains(res)){
			result = true;
		}
		return result;
	}

	public void flush() {
		this.userRepository.flush();
	}

	// 4.3

	public Collection<Citizen> findAttendsByRendezvousId(final int rendezvousId) {
		Collection<Citizen> res;
		res = this.userRepository.findAttendsByRendezvousId(rendezvousId);
		Assert.notNull(res);

		return res;
	}

	// 4.3

	public Citizen findOrganiserByRendezvousId(final int rendezvousId) {
		Citizen res;
		res = this.userRepository.findOrganiserByRendezvousId(rendezvousId);
		Assert.notNull(res);

		return res;
	}

	public Citizen reconstruct(final UserForm userForm, final BindingResult binding) {
		
		Assert.notNull(userForm);
		
		Citizen res = new Citizen();

		if (userForm.getId() != 0)
			res = this.findOne(userForm.getId());
		else
			res = this.create();
		
		res.setName(userForm.getName());
		res.setSurname(userForm.getSurname());
		res.setEmail(userForm.getEmail());
		res.setPhone(userForm.getPhone());
		res.setAddress(userForm.getAddress());
		res.setBirth(userForm.getBirth());
		res.getUserAccount().setUsername(userForm.getUsername());
		res.getUserAccount().setPassword(userForm.getPassword());

		this.validator.validate(res, binding);

		return res;
	}

	public UserForm construct(Citizen user) {
			
		Assert.notNull(user);
		UserForm editUserForm = new UserForm();
		
		editUserForm.setId(user.getId());
		editUserForm.setName(user.getName());
		editUserForm.setSurname(user.getSurname());
		editUserForm.setEmail(user.getEmail());
		editUserForm.setPhone(user.getPhone());
		editUserForm.setAddress(user.getAddress());
		editUserForm.setBirth(user.getBirth());
		editUserForm.setUsername(user.getUserAccount().getUsername());
		editUserForm.setPassword(user.getUserAccount().getPassword());
		editUserForm.setRepeatPassword(user.getUserAccount().getPassword());
		editUserForm.setTermsAndConditions(true);
		
		return editUserForm;
	}

}
