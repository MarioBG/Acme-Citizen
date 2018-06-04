
package controllers;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Citizen;
import domain.Lottery;
import services.CitizenService;
import services.LotteryService;
import services.LotteryTicketService;

@Controller
@RequestMapping("/lottery")
public class LotteryController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private LotteryTicketService lotteryTicketService;

	@Autowired
	private CitizenService citizenService;
	// Constructors ---------------------------------------------------------

	public LotteryController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Lottery> lotterys = lotteryService.findAll();

		Date date = new Date();

		result = new ModelAndView("lottery/list");
		try {
			Citizen principal = citizenService.findByPrincipal();
			result.addObject("principal", principal);
		} catch (Exception e) {
		}

		result.addObject("lotterys", lotterys);
		result.addObject("date", date);

		return result;
	}

	@RequestMapping(value = "/buyTicket", method = RequestMethod.GET)
	public ModelAndView buy(int lotteryId) {

		ModelAndView result;
		lotteryTicketService.buyLottery(lotteryId);
		Collection<Lottery> lotterys = lotteryService.findAll();

		result = new ModelAndView("redirect:myTickets.do");

		try {
			// Chekear el principal para que se sepa cual es el
			Citizen principal = citizenService.findByPrincipal();
			result.addObject("principal", principal);

		} catch (Throwable o) {

		}

		result.addObject("lotterys", lotterys);

		return result;

	}

}
