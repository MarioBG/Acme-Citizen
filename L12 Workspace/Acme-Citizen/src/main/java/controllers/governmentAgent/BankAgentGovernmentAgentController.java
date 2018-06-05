
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.BankAgent;
import domain.GovernmentAgent;
import forms.BankAgentForm;
import services.BankAgentService;
import services.GovernmentAgentService;

@Controller
@RequestMapping("/bankAgent/governmentAgent")
public class BankAgentGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private BankAgentService bankAgentService;

	@Autowired
	private GovernmentAgentService governmentAgentService;

	// Constructors ---------------------------------------------------------

	public BankAgentGovernmentAgentController() {
		super();
	}

	// Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final BankAgent agent = this.bankAgentService.create();
		final BankAgentForm bankAgentForm = this.bankAgentService.construct(agent);

		res = this.createEditModelAndView(bankAgentForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BankAgentForm bankAgentForm, final BindingResult binding) {
		ModelAndView res;
		BankAgent agent;

		if (binding.hasErrors())
			res = this.createEditModelAndView(bankAgentForm, "user.params.error");
		else if (!bankAgentForm.getRepeatPassword().equals(bankAgentForm.getPassword()))
			res = this.createEditModelAndView(bankAgentForm, "user.commit.errorPassword");
		else if (bankAgentForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(bankAgentForm, "user.params.errorTerms");
		else if (!this.governmentAgentService.findByPrincipal().getCanCreateMoney()
				&& bankAgentForm.getCanCreateMoney())
			res = this.createEditModelAndView(bankAgentForm, "user.commit.errorNoCanCreateMoney");
		else
			try {
				agent = this.bankAgentService.reconstruct(bankAgentForm, binding);
				this.bankAgentService.save(agent);
				res = new ModelAndView("redirect:../../welcome/index.do");
			} catch (final Throwable oops) {
				if (oops.getMessage() == null)
					res = this.createEditModelAndView(bankAgentForm, "user.commit.error");
				else
					res = this.createEditModelAndView(bankAgentForm, oops.getMessage());
			}

		return res;
	}

	@RequestMapping(value = "/denied", method = RequestMethod.GET)
	public ModelAndView denied(int agentId) {

		ModelAndView result;
		bankAgentService.stopCreateMoney(agentId);

		result = new ModelAndView("redirect:../../welcome/index.do");

		return result;

	}

	// Ancillary methods
	// ---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final BankAgentForm bankAgentForm) {
		ModelAndView result;

		result = this.createEditModelAndView(bankAgentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BankAgentForm bankAgentForm, final String message) {
		ModelAndView result;

		final GovernmentAgent gov = this.governmentAgentService.findByPrincipal();
		final Boolean canCreateMoney = gov.getCanCreateMoney();

		result = new ModelAndView("bankAgent/register");
		result.addObject("bankAgentForm", bankAgentForm);
		result.addObject("message", message);
		result.addObject("canCreateMoneyParent", canCreateMoney);

		return result;
	}

}
