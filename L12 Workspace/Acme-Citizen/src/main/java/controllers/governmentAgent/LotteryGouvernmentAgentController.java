package controllers.governmentAgent;

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
import domain.GovernmentAgent;
import domain.Lottery;
import services.GovernmentAgentService;
import services.LotteryService;

@Controller
@RequestMapping("/lottery/gouvernmentAgent")
public class LotteryGouvernmentAgentController extends AbstractController {

	// Services ---------------------------------------
	@Autowired
	private LotteryService lotteryService;
	
	@Autowired
	private GovernmentAgentService governmentAgentService;

	// DISPLAY CUANDO ESTAS LOGEADO COMO GOUVERMENT AGENT
	// ---------------------------

	@RequestMapping(value = "/displayMyLottery", method = RequestMethod.GET)
	public ModelAndView display() {
		ModelAndView result;
		GovernmentAgent ga = governmentAgentService.findByPrincipal();
		Collection<Lottery> gaLottery;

		gaLottery = lotteryService.getLotteryByGovernmentAgentId(ga.getId());
		result = new ModelAndView("lottery/display");
		result.addObject("gaLottery", gaLottery);

		return result;
	}

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
				result = new ModelAndView("redirect:displayMyLottery.do");
			} catch (final Throwable oops) {
				String errorMessage = "lottery.commit.error";

				if (oops.getMessage().contains("message.error")) {
					errorMessage = oops.getMessage();
				}
				result = this.createEditModelAndView(lottery, errorMessage);
			}

		return result;
	}

	// DELETE --------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(Lottery lottery, BindingResult bindingResult) {
		ModelAndView result;

		try {
			lotteryService.delete(lottery);
			result = new ModelAndView("redirect:/lottery/gouvernmentAgent/displayMyLottery.do");
		} catch (Throwable oops) {
			result = createEditModelAndView(lottery, "lottery.commit.error");
		}

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
