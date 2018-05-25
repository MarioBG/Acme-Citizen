
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.GovernmentAgentService;
import controllers.AbstractController;
import domain.GovernmentAgent;
import forms.GovernmentAgentForm;

@Controller
@RequestMapping("/governmentagent/governmentagent")
public class GovernmentAgentGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors ---------------------------------------------------------

	public GovernmentAgentGovernmentAgentController() {
		super();
	}

	// Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final GovernmentAgent agent = this.governmentAgentService.create();
		final GovernmentAgentForm governmentAgentForm = this.governmentAgentService.construct(agent);

		res = this.createEditModelAndView(governmentAgentForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final GovernmentAgentForm governmentAgentForm, final BindingResult binding) {
		ModelAndView res;
		GovernmentAgent govAgent;

		if (binding.hasErrors())
			res = this.createEditModelAndView(governmentAgentForm, "user.params.error");
		else if (!governmentAgentForm.getRepeatPassword().equals(governmentAgentForm.getPassword()))
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorPassword");
		else if (governmentAgentForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(governmentAgentForm, "user.params.errorTerms");
		else if (!this.governmentAgentService.findByPrincipal().getCanCreateMoney() && governmentAgentForm.getCanCreateMoney())
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorNoCanCreateMoney");
		else if (!this.governmentAgentService.findByPrincipal().getCanOrganiseElection() && governmentAgentForm.getCanOrganiseElection())
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorNoCanOrganiseElection");
		else
			try {
				govAgent = this.governmentAgentService.reconstruct(governmentAgentForm, binding);
				this.governmentAgentService.save(govAgent);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(governmentAgentForm, "user.commit.error");
			}

		return res;
	}

	// Ancillary methods ---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final GovernmentAgentForm govAgentForm) {
		ModelAndView result;

		result = this.createEditModelAndView(govAgentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final GovernmentAgentForm govAgentForm, final String message) {
		ModelAndView result;

		final GovernmentAgent gov = this.governmentAgentService.findByPrincipal();
		final Boolean canCreateMoney = gov.getCanCreateMoney();
		final Boolean canElection = gov.getCanOrganiseElection();

		result = new ModelAndView("governmentagent/register");
		result.addObject("governmentAgentForm", govAgentForm);
		result.addObject("message", message);
		result.addObject("canCreateMoneyParent", canCreateMoney);
		result.addObject("canOrganiseElectionParent", canElection);

		return result;
	}

}
