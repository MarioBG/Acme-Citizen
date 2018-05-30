
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.WelcomeMessageService;
import controllers.AbstractController;
import domain.WelcomeMessage;
import forms.WelcomeMessageForm;

@Controller
@RequestMapping("/governmentagent/welcomemessage")
public class WelcomeMessageGovernmentAgentController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private WelcomeMessageService	welcomeMessageService;


	// Constructors --------------------------------------------------

	public WelcomeMessageGovernmentAgentController() {
		super();
	}

	// Creation ------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int configurationId) {

		ModelAndView res;
		WelcomeMessage welcomeMessage;
		WelcomeMessageForm form;

		welcomeMessage = this.welcomeMessageService.create(configurationId);
		form = this.welcomeMessageService.construct(welcomeMessage);

		res = this.createEditModelAndView(form);

		return res;
	}

	// Edition -------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int folderId) {

		final ModelAndView result;
		WelcomeMessage welcomeMessage;

		welcomeMessage = this.welcomeMessageService.findOne(folderId);
		final WelcomeMessageForm welcomeMessageForm = this.welcomeMessageService.construct(welcomeMessage);

		result = this.createEditModelAndView(welcomeMessageForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final WelcomeMessageForm folderForm, final BindingResult binding) {

		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(folderForm);
		else
			try {
				final WelcomeMessage welcomeMessage = this.welcomeMessageService.reconstruct(folderForm, binding);
				this.welcomeMessageService.save(welcomeMessage);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(folderForm, "folder.commit.error");
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
			result = new ModelAndView("redirect:/welcomemessage/governmentagent/edit.do");
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

		res = new ModelAndView("advertisement/edit");

		res.addObject("welcomeMessageForm", welcomeMessageForm);
		res.addObject("message", message);

		return res;
	}
}
