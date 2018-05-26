package controllers.bankAgent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.BankAccount;
import services.ActorService;
import services.BankAccountService;

@Controller
@RequestMapping("/bankaccount/bankagent")
public class BankAccountBankAgentController extends AbstractController {

	// Services ---------------------------------------
	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private ActorService actorService;

	// List of all actors ----------------------------------------------

	@RequestMapping(value = "/listActor", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Actor> actors;
		actors = this.actorService.findAll();

		result = new ModelAndView("/bankaccount/bankagent/listActor");
		result.addObject("actors", actors);
		result.addObject("requestURI", "/bankaccount/bankagent/listActor.do");

		return result;
	}

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(final int actorId) {
		ModelAndView result;
		BankAccount bankAccount;

		bankAccount = bankAccountService.create(actorId);
		result = createEditModelAndView(bankAccount);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int bankAccountId) {
		ModelAndView result;
		BankAccount bankAccount;

		bankAccount = bankAccountService.findOne(bankAccountId);

		result = this.createEditModelAndView(bankAccount);
		result.addObject("bankAccount", bankAccount);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BankAccount bankAccount, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(bankAccount);
		else
			try {
				this.bankAccountService.save(bankAccount);
				result = new ModelAndView("redirect:/bankaccount/bankagent/listActor.do");
			} catch (final Throwable oops) {
				String errorMessage = "bankAccount.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(bankAccount, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(BankAccount bankAccount, BindingResult bindingResult) {
		ModelAndView result;

		try {
			bankAccountService.delete(bankAccount);
			result = new ModelAndView("redirect:/bankaccount/bankagent/listActor.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(bankAccount, "bankAccount.commit.error");
		}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(BankAccount bankAccount) {
		ModelAndView result;

		result = createEditModelAndView(bankAccount, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(BankAccount bankAccount, String message) {
		ModelAndView result;

		result = new ModelAndView("bankAccount/edit");

		result.addObject("bankAccount", bankAccount);
		result.addObject("message", message);

		return result;
	}

}
