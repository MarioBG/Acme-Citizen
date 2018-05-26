
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Lottery;
import services.LotteryService;

@Controller
@RequestMapping("/lottery")
public class LotteryController extends AbstractController {

	// Services -------------------------------------------------------------

	// @Autowired
	// private UserService userService;

	@Autowired
	private LotteryService lotteryService;

	// Constructors ---------------------------------------------------------

	public LotteryController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Lottery> lotterys = lotteryService.findAll();

		result = new ModelAndView("lottery/list");
		result.addObject("lotterys", lotterys);

		return result;
	}

}
