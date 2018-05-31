package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import domain.BankAccount;
import services.ActorService;
import services.BankAccountService;

@Controller
@RequestMapping("/bankAccount")
public class BankAccountController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private BankAccountService bankAccountService;

	@Autowired
	private ActorService actorService;

	// Constructors ---------------------------------------------------------

	public BankAccountController() {
		super();
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		BankAccount bankAccount;

		Actor principal = actorService.findByPrincipal();
		result = new ModelAndView("bankAccount/display");
		try {
			bankAccount = bankAccountService.findOne(principal.getBankAccount().getId());
			result.addObject("bankAccount", bankAccount);
		} catch (Exception e) {

		}

		result.addObject("principal", principal);
		return result;
	}

}
