
package controllers;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ElectionService;
import services.GovernmentAgentService;
import domain.Election;
import domain.GovernmentAgent;

@Controller
@RequestMapping("/election")
public class ElectionController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private ElectionService			electionService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors --------------------------------------------------

	public ElectionController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		final ModelAndView result;
		Collection<Election> elections;

		elections = this.electionService.findAll();

		GovernmentAgent governmentAgent = null;
		if (this.governmentAgentService.findByPrincipal() != null)
			governmentAgent = this.governmentAgentService.findByPrincipal();

		result = new ModelAndView("election/list");
		result.addObject("elections", elections);
		result.addObject("governmentAgent", governmentAgent);
		result.addObject("requestURI", "election/list.do");

		return result;
	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int electionId) {

		final Election election = this.electionService.findOne(electionId);

		Date date;
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.parse(sdf.format(new Date()));
		} catch (final Throwable oops) {
			date = new Date();
		}

		final ModelAndView result = new ModelAndView("election/display");
		result.addObject("election", election);
		result.addObject("date", date);

		return result;

	}

	// Ancillary methods ---------------------------------------------

}
