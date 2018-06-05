package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.BankAccount;
import domain.BankAgent;
import domain.EconomicTransaction;
import domain.GovernmentAgent;
import services.ActorService;
import services.BankAccountService;
import services.BankAgentService;
import services.EconomicTransactionService;
import services.GovernmentAgentService;

@Controller
@RequestMapping("/transaction")
public class EconomicTransactionCreateMoneyController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private ActorService actorService;

	@Autowired
	private BankAgentService bankAgentService;

	@Autowired
	private GovernmentAgentService governmentAgentService;

	@Autowired
	private EconomicTransactionService economicTransactionService;

	// Create Money --------------------------------

	@RequestMapping(value = "/createMoney", method = RequestMethod.GET)
	public ModelAndView createMoney() {

		ModelAndView result;
		EconomicTransaction economicTransaction;
		economicTransaction = this.economicTransactionService.createMoney();

		Collection<BankAccount> bankAccounts = this.bankAccountService.findAll();
		result = createEditModelAndView(economicTransaction);
		result.addObject("bankAccounts", bankAccounts);

		try {
			BankAgent bankAgent = this.bankAgentService.findByPrincipal();
			GovernmentAgent ga = this.governmentAgentService.findByPrincipal();
			if (bankAgent != null) {
				result.addObject("principal", bankAgent);
			} else if (ga != null) {
				result.addObject("principal", ga);
			}

		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	@RequestMapping(value = "/createMoney", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EconomicTransaction economicTransaction, final BindingResult binding) {
		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(economicTransaction);
		else
			try {
				this.economicTransactionService.save2(economicTransaction);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				String errorMessage = "economicTransaction.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(economicTransaction, errorMessage);
			}

		return result;
	}

	// Listing money
	// made--------------------------------------------------------------

	@RequestMapping(value = "/listMoneyCreate", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor principal = this.actorService.findByPrincipal();
		Collection<EconomicTransaction> bankAgentMoney = economicTransactionService.findBankAgentDoMoney();
		Collection<EconomicTransaction> governmentAgentMoney = economicTransactionService.findGovernmentAgentDoMoney();

		result = new ModelAndView("transaction/listMoneyCreate");

		result.addObject("bankAgentMoney", bankAgentMoney);
		result.addObject("governmentAgentMoney", governmentAgentMoney);
		result.addObject("principal", principal);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransaction) {

		ModelAndView result;

		result = this.createEditModelAndView(economicTransaction, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransaction, final String message) {

		ModelAndView result;
		Actor principal = this.actorService.findByPrincipal();

		result = new ModelAndView("transaction/createMoney");

		result.addObject("principal", principal);
		result.addObject("economicTransaction", economicTransaction);
		result.addObject("message", message);

		return result;
	}

}
