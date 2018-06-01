
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.WelcomeMessageRepository;
import domain.WelcomeMessage;
import forms.WelcomeMessageForm;

@Service
@Transactional
public class WelcomeMessageService {

	// Managed repository
	@Autowired
	private WelcomeMessageRepository	welcomeMessageRepository;

	// Supported services

	@Autowired
	private ConfigurationService		configurationService;

	@Autowired
	private GovernmentAgentService		governmentAgentService;

	@Autowired
	private Validator					validator;


	// Constructors
	public WelcomeMessageService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public WelcomeMessage create(final int configurationId) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

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

		Assert.notNull(this.governmentAgentService.findByPrincipal());
		if (welcomeMessage.getId() == 0)
			Assert.isTrue(this.welcomeMessageRepository.getWelcomeMessageByLocale(welcomeMessage.getLanguageCode()) == null);
		final WelcomeMessage result = this.welcomeMessageRepository.save(welcomeMessage);

		if (welcomeMessage.getId() == 0) {
			final Collection<WelcomeMessage> welcomes = this.configurationService.findActive().getWelcomeMessages();
			welcomes.add(result);
			this.configurationService.findActive().setWelcomeMessages(welcomes);
		}
		return result;
	}
	public void delete(final WelcomeMessage welcomeMessage) {
		Assert.notNull(this.governmentAgentService.findByPrincipal());
		final Collection<WelcomeMessage> welcomes = this.configurationService.findActive().getWelcomeMessages();
		welcomes.remove(welcomeMessage);
		this.configurationService.findActive().setWelcomeMessages(welcomes);

		this.welcomeMessageRepository.delete(welcomeMessage);
	}
	// Ancillary methods

	public String getWelcomeMessageForLocale(final String languageCode) {
		String ans = "";
		final WelcomeMessage wm = this.welcomeMessageRepository.getWelcomeMessageByLocale(languageCode);
		if (wm == null)
			ans = null;
		else
			ans = wm.getContent();
		return ans;
	}

	public WelcomeMessageForm construct(final WelcomeMessage welcomeMessage) {

		Assert.notNull(welcomeMessage);

		WelcomeMessageForm welcomeMessageForm;

		welcomeMessageForm = new WelcomeMessageForm();

		welcomeMessageForm.setId(welcomeMessage.getId());
		welcomeMessageForm.setLanguageCode(welcomeMessage.getLanguageCode());
		welcomeMessageForm.setContent(welcomeMessage.getContent());

		return welcomeMessageForm;
	}

	public WelcomeMessage reconstruct(final WelcomeMessageForm advertisementForm, final BindingResult binding) {

		Assert.notNull(advertisementForm);

		WelcomeMessage welcomeMessage;

		if (advertisementForm.getId() != 0)
			welcomeMessage = this.findOne(advertisementForm.getId());
		else
			welcomeMessage = this.create(this.configurationService.findActive().getId());

		welcomeMessage.setLanguageCode(advertisementForm.getLanguageCode());
		welcomeMessage.setContent(advertisementForm.getContent());

		if (binding != null)
			this.validator.validate(welcomeMessage, binding);

		return welcomeMessage;
	}

}
