
package controllers.governmentAgent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.WelcomeMessageService;
import controllers.AbstractController;
import domain.Configuration;
import domain.WelcomeMessage;

@Controller
@RequestMapping("/configuration/governmentAgent")
public class ConfigurationGovernmentAgentController extends AbstractController {

	// Services --------------------------------
	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private WelcomeMessageService	welcomeMessageService;


	// Constructors ----------------------------
	public ConfigurationGovernmentAgentController() {
		super();
	}

	// Edit -----------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView res;
		Configuration configuration;

		configuration = this.configurationService.findAll().iterator().next();

		res = this.createEditModelAndView(configuration);
		return res;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Configuration configuration, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(configuration);
		else
			try {
				this.configurationService.save(configuration);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(configuration, "configuration.commit.error");
			}
		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final Configuration configuration) {

		ModelAndView result;

		result = this.createEditModelAndView(configuration, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final Configuration configuration, final String messageCode) {

		ModelAndView result;
		final Collection<WelcomeMessage> welcomeMessages = this.welcomeMessageService.findAll();

		result = new ModelAndView("configuration/edit");
		result.addObject("configuration", configuration);
		result.addObject("welcomeMessages", welcomeMessages);
		result.addObject("message", messageCode);

		return result;
	}

}
