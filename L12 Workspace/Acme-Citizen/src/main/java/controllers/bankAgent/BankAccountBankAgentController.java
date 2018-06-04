
package controllers.bankAgent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Actor;
import domain.BankAccount;
import services.ActorService;
import services.BankAccountService;

@Controller
@RequestMapping("/bankaccount/bankAgent")
public class BankAccountBankAgentController extends AbstractController {

	// Services ---------------------------------------
	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private ActorService actorService;

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		BankAccount bankAccount;
		final Collection<Actor> actors = this.actorService.findAllWithoutBankAccount();

		bankAccount = this.bankAccountService.create();
		result = this.createEditModelAndView(bankAccount);
		result.addObject("actors", actors);

		return result;
	}

	// Edition ----------------------------------------------------------------

	// @RequestMapping(value = "/edit", method = RequestMethod.GET)
	// public ModelAndView edit(@RequestParam final int bankAccountId) {
	// ModelAndView result;
	// BankAccount bankAccount;
	//
	// bankAccount = bankAccountService.findOne(bankAccountId);
	//
	// result = this.createEditModelAndView(bankAccount);
	// result.addObject("bankAccount", bankAccount);
	//
	// return result;
	// }

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final BankAccount bankAccount, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(bankAccount);
		else
			try {
				this.bankAccountService.save(bankAccount);
				result = new ModelAndView("redirect:../../welcome/index.do");
			} catch (final Throwable oops) {
				String errorMessage = "bankAccount.commit.error";

				if (oops.getMessage().contains("message.error"))
					errorMessage = oops.getMessage();
				result = this.createEditModelAndView(bankAccount, errorMessage);
			}

		return result;
	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(final BankAccount bankAccount) {
		ModelAndView result;

		result = this.createEditModelAndView(bankAccount, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final BankAccount bankAccount, final String message) {
		ModelAndView result;
		final Collection<Actor> actors = this.actorService.findAllWithoutBankAccount();

		result = new ModelAndView("bankAccount/edit");

		result.addObject("bankAccount", bankAccount);
		result.addObject("message", message);
		result.addObject("actors", actors);

		return result;
	}

}
