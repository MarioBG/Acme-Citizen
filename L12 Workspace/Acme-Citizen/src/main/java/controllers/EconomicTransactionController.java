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
public class EconomicTransactionController extends AbstractController {

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

	// Create --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EconomicTransaction economicTransaction;

		Collection<BankAccount> bankAccounts = this.bankAccountService.findAll();
		Actor principal = this.actorService.findByPrincipal();
		bankAccounts.remove(principal.getBankAccount());

		economicTransaction = this.economicTransactionService.create();

		result = createEditModelAndView(economicTransaction);
		result.addObject("bankAccounts", bankAccounts);
		result.addObject("principal", principal);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final EconomicTransaction economicTransaction, final BindingResult binding) {
		ModelAndView result;
		Collection<BankAccount> bankAccounts = this.bankAccountService.findAll();
		Actor principal = this.actorService.findByPrincipal();
		bankAccounts.remove(principal.getBankAccount());

		if (binding.hasErrors() || !(this.economicTransactionService.checkMoney(economicTransaction))) {
			result = this.createEditModelAndView(economicTransaction, "economicTransaction.commit.error");
		} else
			try {
				this.economicTransactionService.save(economicTransaction);
				result = new ModelAndView("redirect:../welcome/index.do");
			} catch (final Throwable oops) {
				String errorMessage = "economicTransaction.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(economicTransaction, errorMessage);
			}

		result.addObject("bankAccounts", bankAccounts);
		result.addObject("principal", principal);
		return result;
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Actor principal = this.actorService.findByPrincipal();
		Collection<EconomicTransaction> debtorTransactions = economicTransactionService
				.findDebtorTransactionByActorId(principal.getId());
		Collection<EconomicTransaction> creditorTransactions = economicTransactionService
				.findCreditorTransactionByActorId(principal.getId());

		result = new ModelAndView("transaction/list");
		result.addObject("debtorTransactions", debtorTransactions);
		result.addObject("creditorTransactions", creditorTransactions);
		result.addObject("principal", principal);

		try {
			BankAgent bankAgent = this.bankAgentService.findByPrincipal();
			GovernmentAgent ga = this.governmentAgentService.findByPrincipal();
			if (bankAgent != null) {
				result.addObject("bankAgent", bankAgent);
			} else if (ga != null) {
				result.addObject("ga", ga);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransaction) {

		ModelAndView result;

		result = this.createEditModelAndView(economicTransaction, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransaction, final String message) {

		ModelAndView result;

		result = new ModelAndView("transaction/edit");
		result.addObject("economicTransaction", economicTransaction);
		result.addObject("message", message);
		Actor principal = this.actorService.findByPrincipal();
		result.addObject("principal", principal);

		return result;
	}

}
