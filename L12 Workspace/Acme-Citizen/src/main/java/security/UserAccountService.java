
package security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import domain.Actor;

@Service
@Transactional
public class UserAccountService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private UserAccountRepository	userAccountRepository;


	// Supporting services ----------------------------------------------------

	// Constructor -----------------------------------------------------------

	public UserAccountService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------
	public UserAccount create() {
		UserAccount ua;

		ua = new UserAccount();

		return ua;
	}

	public void save(final UserAccount userAccount) {
		Assert.notNull(userAccount);

		this.userAccountRepository.save(userAccount);
	}

	public void delete(final UserAccount userAccount) {
		this.userAccountRepository.delete(userAccount);
	}

	public Collection<UserAccount> findAll() {
		Collection<UserAccount> result;

		result = this.userAccountRepository.findAll();

		return result;
	}

	public UserAccount findOne(final int userAccountId) {
		UserAccount result;

		result = this.userAccountRepository.findOne(userAccountId);

		return result;
	}

	// Other business methods -------------------------------------------------
	public UserAccount findByActor(final Actor actor) {
		Assert.notNull(actor);

		UserAccount result;

		result = this.userAccountRepository.findByActorId(actor.getId());

		return result;
	}

	public UserAccount findByUsername(final String username) {
		return this.userAccountRepository.findByUsername(username);
	}

}
