
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.CitizenService;
import controllers.AbstractController;
import domain.Citizen;
import forms.CitizenForm;

@Controller
@RequestMapping("/citizen/governmentAgent")
public class CitizenGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CitizenService	citizenService;


	// Constructors ---------------------------------------------------------

	public CitizenGovernmentAgentController() {
		super();
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final Citizen citizen = this.citizenService.create();
		final CitizenForm citizenForm = this.citizenService.construct(citizen);
		;
		res = this.createEditModelAndView(citizenForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CitizenForm citizenForm, final BindingResult binding) {
		ModelAndView res;
		Citizen citizen;

		if (binding.hasErrors())
			res = this.createEditModelAndView(citizenForm, "user.params.error");
		else if (!citizenForm.getRepeatPassword().equals(citizenForm.getPassword()))
			res = this.createEditModelAndView(citizenForm, "user.commit.errorPassword");
		else if (citizenForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(citizenForm, "user.params.errorTerms");
		else
			try {
				citizen = this.citizenService.reconstruct(citizenForm, binding);
				this.citizenService.save(citizen);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				if (oops.getMessage() == null)
					res = this.createEditModelAndView(citizenForm, "user.commit.error");
				else
					res = this.createEditModelAndView(citizenForm, oops.getMessage());
			}

		return res;
	}
	// Ancillary methods ---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CitizenForm citizenForm) {
		ModelAndView result;

		result = this.createEditModelAndView(citizenForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CitizenForm citizenForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("citizen/register");
		result.addObject("citizenForm", citizenForm);
		result.addObject("message", message);

		return result;
	}

}
