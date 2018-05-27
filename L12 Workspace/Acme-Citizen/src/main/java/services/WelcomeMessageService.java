
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import repositories.WelcomeMessageRepository;
import domain.WelcomeMessage;

@Service
@Transactional
public class WelcomeMessageService {

	// Managed repository
	@Autowired
	private WelcomeMessageRepository	welcomeMessageRepository;

	// Supported services

	@Autowired
	private ConfigurationService		configurationService;


	// Constructors
	public WelcomeMessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public WelcomeMessage create() {

		final WelcomeMessage welcomeMessage = new WelcomeMessage();

		return welcomeMessage;
	}

	public WelcomeMessage findOne(final int welcomeMessageId) {

		WelcomeMessage result = null;
		result = this.welcomeMessageRepository.findOne(welcomeMessageId);
		return result;
	}

	public Collection<WelcomeMessage> findAll() {

		Collection<WelcomeMessage> result = null;
		result = this.welcomeMessageRepository.findAll();
		return result;
	}

	public WelcomeMessage save(final WelcomeMessage welcomeMessage) {

		final WelcomeMessage result = this.welcomeMessageRepository.save(welcomeMessage);

		if (welcomeMessage.getId() == 0) {
			final Collection<WelcomeMessage> welcomes = this.configurationService.findActive().getWelcomeMessages();
			welcomes.add(result);
			this.configurationService.findActive().setWelcomeMessages(welcomes);
		}
		return result;
	}
	public void delete(final WelcomeMessage welcomeMessage) {
		final Collection<WelcomeMessage> welcomes = this.configurationService.findActive().getWelcomeMessages();
		welcomes.remove(welcomeMessage);
		this.configurationService.findActive().setWelcomeMessages(welcomes);

		this.welcomeMessageRepository.delete(welcomeMessage);
	}

	// Ancillary methods

	public String getWelcomeMessageForLocale(final String languageCode) {
		return this.welcomeMessageRepository.getWelcomeMessageByLocale(languageCode).getContent();
	}

}
