package controllers;

import java.util.Collection;

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
@RequestMapping("/lotteryTicket")
public class LotteryTicketController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private LotteryTicketService lotteryTicketService;

	@Autowired
	private LotteryService lotteryService;
	@Autowired
	private CitizenService citizenService;

	// Constructors ---------------------------------------------------------

	public LotteryTicketController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Lottery> lotterys = lotteryService.findAllAfterToday();

		result = new ModelAndView("lottery/list");
		result.addObject("lotterys", lotterys);

		return result;
	}

	@RequestMapping(value = "/buy", method = RequestMethod.GET)
	public ModelAndView buy(int lotteryId) {

		ModelAndView result;
		lotteryTicketService.buyLottery(lotteryId);
		Collection<Lottery> lotterys;

		lotterys = lotteryService.findAllAfterToday();
		result = new ModelAndView("redirect:/lotteryTicket/list.do");

		try {
			// Chekear el principal para que se sepa cual es el
			Citizen principal = citizenService.findByPrincipal();
			result.addObject("principal", principal);

		} catch (Throwable o) {

		}

		result.addObject("lotterys", lotterys);

		return result;

	}

	// Ancillary methods ---------------------------------------------

}
