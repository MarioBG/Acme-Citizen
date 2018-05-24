
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CitizenService;
import domain.Citizen;

@Controller
@RequestMapping("/citizen")
public class CitizenController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CitizenService	citizenService;


	// Constructors ---------------------------------------------------------

	public CitizenController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Citizen> citizens;
		Citizen principal;

		citizens = this.citizenService.findAll();
		principal = this.citizenService.findByPrincipal();
		if (principal != null)
			citizens.remove(principal);

		result = new ModelAndView("citizen/list");
		result.addObject("citizens", citizens);
		result.addObject("principal", principal);
		result.addObject("requestURI", "citizen/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam final int userId) {
		ModelAndView result;
		Citizen citizen;

		citizen = this.citizenService.findOne(userId);

		result = new ModelAndView("citizen/display");
		result.addObject("citizen", citizen);
		result.addObject("requestURI", "citizen/display.do");

		return result;
	}

}
