package controllers.citizen;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.Citizen;
import domain.LotteryTicket;
import services.CitizenService;
import services.LotteryTicketService;

@Controller
@RequestMapping("/lottery")
public class LotteryTicketCitizenController extends AbstractController{

	// Services ---------------------------------------
	@Autowired
	private LotteryTicketService lotteryTicketService;

	@Autowired
	private CitizenService citizenService;

	@RequestMapping(value = "/myTickets", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		Citizen citizen = citizenService.findByPrincipal();
		Collection<LotteryTicket> lotterys;

		lotterys = lotteryTicketService.findAllByCitizenId(citizen.getId());
		result = new ModelAndView("lottery/myTickets");
		result.addObject("lotterys", lotterys);

		return result;
	}

}
