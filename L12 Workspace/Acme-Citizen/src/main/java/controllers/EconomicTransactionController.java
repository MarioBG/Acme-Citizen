package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.EconomicTransaction;
import services.EconomicTransactionService;

@Controller
@RequestMapping("/transactions")
public class EconomicTransactionController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private EconomicTransactionService economicTransactionService;

	// Constructors ---------------------------------------------------------

	public EconomicTransactionController() {
		super();
	}

	// Create --------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		ModelAndView result;
		EconomicTransaction economicTransaction;

		economicTransaction = this.economicTransactionService.create();

		result = createEditModelAndView(economicTransaction);

		return result;
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(final int actorId) {
		ModelAndView result;
		Collection<EconomicTransaction> debtorTransactions = economicTransactionService
				.findDebtorTransactionByActorId(actorId);
		Collection<EconomicTransaction> creditorTransactions = economicTransactionService
				.findCreditorTransactionByActorId(actorId);

		result = new ModelAndView("economicTransaction/list");
		result.addObject("debtorTransactions", debtorTransactions);
		result.addObject("creditorTransactions", creditorTransactions);

		return result;
	}

	// Ancillary methods ---------------------------------------------

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransacion) {

		ModelAndView result;

		result = this.createEditModelAndView(economicTransacion, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final EconomicTransaction economicTransacion,
			final String messageCode) {

		ModelAndView result;

		result = new ModelAndView("economicTransacion/edit");

		return result;
	}

}
