
package services;

import java.util.ArrayList;
import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import domain.Configuration;
import domain.WelcomeMessage;

@Service
@Transactional
public class ConfigurationService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors -----------------------------------------------------------

	public ConfigurationService() {
		super();
	}

	// Simple CRUD methods

	public Configuration create() {
		Configuration configuration;
		configuration = new Configuration();
		configuration.setWelcomeMessages(new ArrayList<WelcomeMessage>());
		return configuration;
	}

	public Collection<Configuration> findAll() {
		Collection<Configuration> res;
		res = this.configurationRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Configuration findOne(final int configuration) {
		Assert.isTrue(configuration != 0);
		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.findOne(configuration);
		Assert.notNull(res);
		return res;
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.notNull(this.governmentAgentService.findByPrincipal());
		Configuration res;
		res = this.configurationRepository.save(configuration);
		return res;
	}

	public void delete(final Configuration configuration) {
		Assert.notNull(configuration);
		Assert.isTrue(configuration.getId() != 0);
		Assert.isTrue(this.configurationRepository.exists(configuration.getId()));
		this.configurationRepository.delete(configuration);
	}

	public void flush() {
		this.configurationRepository.flush();
	}

	public Collection<Configuration> findAllByAdmin() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		Collection<Configuration> res;
		res = this.findAll();
		Assert.notNull(res);
		return res;
	}

	public Configuration findActive() {
		Configuration res;
		res = (Configuration) this.findAll().toArray()[0];

		return res;
	}

}
