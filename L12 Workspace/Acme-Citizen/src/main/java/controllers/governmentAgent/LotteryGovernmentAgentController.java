package controllers.governmentAgent;

import java.util.Collection;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import controllers.AbstractController;
import domain.GovernmentAgent;
import domain.Lottery;
import domain.LotteryTicket;
import services.GovernmentAgentService;
import services.LotteryService;

@Controller
@RequestMapping("/lottery/governmentAgent")
public class LotteryGovernmentAgentController extends AbstractController {

	// Services ---------------------------------------
	@Autowired
	private LotteryService lotteryService;

	@Autowired
	private GovernmentAgentService governmentAgentService;

	// Creation ---------------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;
		Lottery lottery;

		lottery = lotteryService.create();
		result = createEditModelAndView(lottery);

		return result;
	}

	// Edition ----------------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int lotteryId) {
		ModelAndView result;
		Lottery lottery;

		lottery = lotteryService.findOne(lotteryId);

		result = this.createEditModelAndView(lottery);
		result.addObject("lottery", lottery);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Lottery lottery, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors())
			result = this.createEditModelAndView(lottery);
		else
			try {
				this.lotteryService.save(lottery);
				result = new ModelAndView("redirect:MyLotterys.do");
			} catch (final Throwable oops) {
				String errorMessage = "lottery.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(lottery, errorMessage);
			}

		return result;
	}

	// MIS LOTERIAS CREADAS (AGENTE GUBERNAMENTAL)

	@RequestMapping(value = "/MyLotterys", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		GovernmentAgent ga = governmentAgentService.findByPrincipal();
		Collection<Lottery> lotterys;
		Date date = new Date();

		lotterys = lotteryService.getLotteryByGovernmentAgentId(ga.getId());
		result = new ModelAndView("lottery/MyLotterys");
		result.addObject("lotterys", lotterys);
		result.addObject("date", date);
		result.addObject("principal", ga);

		return result;
	}

	@RequestMapping(value = "/makeWinner", method = RequestMethod.GET)
	public ModelAndView makeWinner(int lotteryId) {

		ModelAndView result;

		Lottery lottery = this.lotteryService.findOne(lotteryId);
		Collection<LotteryTicket> lotteryTickets = lottery.getLotteryTickets();
		Integer tam = lotteryTickets.size();
		lotteryService.lotteryWinner(lotteryId);

		result = new ModelAndView("redirect:MyLotterys.do");
		result.addObject("lotteryTickets", lotteryTickets);
		result.addObject("tam", tam);

		return result;

	}

	// METODOS AUXILIARES -------------------------------------

	protected ModelAndView createEditModelAndView(Lottery lottery) {
		ModelAndView result;

		result = createEditModelAndView(lottery, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(Lottery lottery, String message) {
		ModelAndView result;

		result = new ModelAndView("lottery/edit");

		result.addObject("lottery", lottery);
		result.addObject("message", message);

		return result;
	}
}
