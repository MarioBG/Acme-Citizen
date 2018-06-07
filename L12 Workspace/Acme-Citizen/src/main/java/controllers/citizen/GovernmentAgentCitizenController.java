
package controllers.citizen;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.GovernmentAgentService;
import services.PetitionService;
import controllers.AbstractController;
import domain.GovernmentAgent;
import domain.Petition;

@Controller
@RequestMapping("/governmentAgent/citizen")
public class GovernmentAgentCitizenController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private GovernmentAgentService	governmentAgentService;

	@Autowired
	private PetitionService			petitionService;


	// Constructors --------------------------------------------------

	public GovernmentAgentCitizenController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/addGovernmentAgents", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int petitionId) {

		Collection<GovernmentAgent> governmentAgents;
		Petition petition;

		governmentAgents = this.governmentAgentService.findAll();
		petition = this.petitionService.findOne(petitionId);

		final ModelAndView result = new ModelAndView("governmentAgent/list");
		result.addObject("governmentAgents", governmentAgents);
		result.addObject("petition", petition);
		result.addObject("requestURI", "governmentAgent/citizen/addGovernmentAgents.do");

		return result;
	}

}
