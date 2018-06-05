
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CitizenService;
import services.GovernmentAgentService;
import services.PetitionService;
import domain.Citizen;
import domain.GovernmentAgent;
import domain.Petition;

@Controller
@RequestMapping("/petition")
public class PetitionController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private PetitionService			petitionService;

	@Autowired
	private CitizenService			citizenService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors --------------------------------------------------

	public PetitionController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam(required = false) final Integer citizenId) {

		final ModelAndView result;
		Collection<Petition> petitions = new ArrayList<Petition>();
		Citizen citizen = null;

		if (this.governmentAgentService.findByPrincipal() == null) {
			if (citizenId != null) {
				Assert.notNull(this.citizenService.findOne(citizenId));
				citizen = this.citizenService.findOne(citizenId);
				petitions = this.petitionService.findByCitizenIdNonDeleted(citizenId);
			} else
				petitions = this.petitionService.findNonDeleted();
		} else if (this.governmentAgentService.findByPrincipal() != null)
			if (citizenId != null) {
				Assert.notNull(this.citizenService.findOne(citizenId));
				citizen = this.citizenService.findOne(citizenId);
				petitions = this.petitionService.findByCitizenId(citizenId);
			} else
				petitions = this.petitionService.findAll();

		result = new ModelAndView("petition/list");
		result.addObject("petitions", petitions);
		result.addObject("citizen", citizen);
		result.addObject("requestURI", "petition/list.do");

		return result;
	}
	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int petitionId) {

		final Petition petition = this.petitionService.findOne(petitionId);
		GovernmentAgent governmentAgent = null;
		if (this.governmentAgentService.findByPrincipal() != null)
			governmentAgent = this.governmentAgentService.findByPrincipal();

		final ModelAndView result = new ModelAndView("petition/display");
		result.addObject("petition", petition);
		result.addObject("governmentAgent", governmentAgent);

		return result;

	}

	// Ancillary methods ---------------------------------------------

}
