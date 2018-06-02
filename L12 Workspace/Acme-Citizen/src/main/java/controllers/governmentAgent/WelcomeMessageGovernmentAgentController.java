
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ConfigurationService;
import services.WelcomeMessageService;
import controllers.AbstractController;
import domain.WelcomeMessage;
import forms.WelcomeMessageForm;

@Controller
@RequestMapping("/welcomemessage/governmentAgent")
public class WelcomeMessageGovernmentAgentController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private WelcomeMessageService	welcomeMessageService;

	@Autowired
	private ConfigurationService	configurationService;


	// Constructors --------------------------------------------------

	public WelcomeMessageGovernmentAgentController() {
		super();
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView res;
		WelcomeMessage welcomeMessage;
		WelcomeMessageForm form;

		welcomeMessage = this.welcomeMessageService.create(this.configurationService.findActive().getId());
		form = this.welcomeMessageService.construct(welcomeMessage);

		res = this.createEditModelAndView(form);

		return res;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int welcomeMessageId) {

		final ModelAndView result;
		WelcomeMessage welcomeMessage;

		welcomeMessage = this.welcomeMessageService.findOne(welcomeMessageId);
		final WelcomeMessageForm welcomeMessageForm = this.welcomeMessageService.construct(welcomeMessage);

		result = this.createEditModelAndView(welcomeMessageForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final WelcomeMessageForm welcomeMessageForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(welcomeMessageForm);
		else
			try {
				final WelcomeMessage welcomeMessage = this.welcomeMessageService.reconstruct(welcomeMessageForm, binding);
				this.welcomeMessageService.save(welcomeMessage);
				result = new ModelAndView("redirect:/configuration/governmentAgent/edit.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(welcomeMessageForm, "folder.commit.error");
			}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int welcomeMessageId) {
		ModelAndView result;
		WelcomeMessage welcomeMessage;
		welcomeMessage = this.welcomeMessageService.findOne(welcomeMessageId);

		try {
			this.welcomeMessageService.delete(welcomeMessage);
			result = new ModelAndView("redirect:/welcome/index.do");
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:/configuration/governmentAgent/edit.do");
		}

		return result;

	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final WelcomeMessageForm welcomeMessageForm) {
		ModelAndView res;

		res = this.createEditModelAndView(welcomeMessageForm, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(final WelcomeMessageForm welcomeMessageForm, final String message) {
		ModelAndView res;

		res = new ModelAndView("welcomemessage/edit");

		res.addObject("welcomeMessageForm", welcomeMessageForm);
		res.addObject("message", message);

		return res;
	}
}
