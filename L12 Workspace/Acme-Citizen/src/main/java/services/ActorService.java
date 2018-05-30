
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import domain.Actor;
import domain.Folder;
import domain.GovernmentAgent;
import repositories.ActorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;

@Service
@Transactional
public class ActorService {

	// Managed repository
	@Autowired
	private ActorRepository actorRepository;

	// Constructors
	public ActorService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Actor findOne(final int actorId) {

		Actor result = null;
		result = this.actorRepository.findOne(actorId);
		return result;
	}

	public Collection<Actor> findAll() {

		Collection<Actor> result = null;
		result = this.actorRepository.findAll();
		return result;
	}

	public Actor save(final Actor actor) {

		Actor result;

		result = this.actorRepository.save(actor);

		return result;
	}

	// Ancillary methods

	public Actor findActorByUserAccountId(final int uA) {

		final Actor actor = this.actorRepository.findActorByUserAccountId(uA);
		return actor;
	}

	public Actor findByPrincipal() {

		Actor actor;

		final UserAccount principalUserAccount = LoginService.getPrincipal();
		if (principalUserAccount == null)
			actor = null;
		else
			actor = this.actorRepository.findActorByUserAccountId(principalUserAccount.getId());

		return actor;
	}

	public String getType(final UserAccount userAccount) {
		final List<Authority> authorities = new ArrayList<Authority>(userAccount.getAuthorities());

		return authorities.get(0).getAuthority();
	}

	public Collection<Folder> findFoldersByUserAccountId(final int userAccountId) {
		Collection<Folder> result = null;
		result = this.actorRepository.findFoldersByUserAccountId(userAccountId);
		return result;
	}

	public Actor findSenderByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findSenderByMessageId(messageId);

		return result;
	}

	public Actor findRecipientByMessageId(final int messageId) {

		Actor result;

		result = this.actorRepository.findRecipientByMessageId(messageId);

		return result;
	}

	public String generateNif(final GovernmentAgent creator) {
		final Character[] letras = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P',
				'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };
		final Random rand = new Random();
		String nif = "";
		nif += creator.getRegisterCode() + "-";
		Integer num = rand.nextInt(99999999);
		final Integer num2 = num;
		Integer prod = num % 10;
		if (prod == 0)
			prod = 1;
		while (num > 0) {
			int next = num % 10;
			if (next == 0)
				next = 1;
			prod = prod * next;
			num = num / 10;
			if (prod == 0)
				prod = 1;
		}
		nif += String.format("%08d", num2);
		nif += letras[(prod - 1) % 26];
		if (this.actorRepository.findActorByNif(nif) != null)
			nif = this.generateNif(creator);
		return nif;
	}

	public Collection<Actor> findAllWithoutBankAccount() {
		return this.actorRepository.findAllWithoutBankAccount();
	}

}
