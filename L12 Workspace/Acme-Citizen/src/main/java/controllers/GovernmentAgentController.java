
package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ChirpService;
import services.GovernmentAgentService;
import domain.Chirp;
import domain.GovernmentAgent;

@Controller
@RequestMapping("/governmentAgent")
public class GovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private ChirpService			chirpService;


	// Constructors ---------------------------------------------------------

	public GovernmentAgentController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		ModelAndView result;
		Collection<GovernmentAgent> agents;

		agents = this.governmentAgentService.findAll();

		result = new ModelAndView("governmentAgent/list");
		result.addObject("governmentAgents", agents);
		result.addObject("requestURI", "governmentAgent/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int governmentAgentId) {
		ModelAndView result;
		GovernmentAgent govAgent;
		final Collection<Chirp> chirps;

		govAgent = this.governmentAgentService.findOne(governmentAgentId);
		chirps = this.chirpService.findByGovernmentAgentId(govAgent.getId());

		result = new ModelAndView("governmentAgent/display");
		result.addObject("governmentAgent", govAgent);
		result.addObject("chirps", chirps);
		result.addObject("requestURI", "governmentAgent/display.do");

		return result;
	}

}
