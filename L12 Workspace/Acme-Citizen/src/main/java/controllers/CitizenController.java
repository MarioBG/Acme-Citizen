
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidatureService;
import services.CitizenService;
import domain.Candidature;
import domain.Citizen;

@Controller
@RequestMapping("/citizen")
public class CitizenController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CitizenService		citizenService;

	@Autowired
	private CandidatureService	candidatureService;


	// Constructors ---------------------------------------------------------

	public CitizenController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<Citizen> citizens;

		citizens = this.citizenService.findAll();

		result = new ModelAndView("citizen/list");
		result.addObject("citizens", citizens);
		result.addObject("requestURI", "citizen/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam final int citizenId) {
		ModelAndView result;
		Citizen citizen;
		Collection<Candidature> candidatures;

		citizen = this.citizenService.findOne(citizenId);
		candidatures = this.candidatureService.findByCitizenId(citizen.getId());

		result = new ModelAndView("citizen/display");
		result.addObject("citizen", citizen);
		result.addObject("candidatures", candidatures);
		result.addObject("requestURI", "citizen/display.do");

		return result;
	}

}
