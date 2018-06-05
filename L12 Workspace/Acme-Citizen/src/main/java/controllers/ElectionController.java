
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidatureService;
import services.CitizenService;
import services.ConfigurationService;
import services.ElectionService;
import services.GovernmentAgentService;
import domain.Candidature;
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

	@Autowired
	private CandidatureService		candidatureService;

	@Autowired
	private CitizenService			citizenService;

	@Autowired
	private ConfigurationService	configurationService;


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

		Boolean hasVoted = null;
		final Election election = this.electionService.findOne(electionId);
		int daysCelebration;
		int daysCandidature;
		Collection<Candidature> participatingCandidatures = new ArrayList<Candidature>();
		daysCelebration = this.getDayDifference(election.getCelebrationDate(), new Date());
		daysCandidature = this.getDayDifference(election.getCandidatureDate(), new Date());
		final String countryFlag = this.configurationService.findActive().getCountryFlag();

		if (this.citizenService.findByPrincipal() != null) {
			participatingCandidatures = this.candidatureService.findByCitizenId(this.citizenService.findByPrincipal().getId());
			if (this.citizenService.findByPrincipal().getElections().contains(this.electionService.findOne(electionId)))
				hasVoted = true;
			else
				hasVoted = false;
		}

		final ModelAndView result = new ModelAndView("election/display");
		result.addObject("election", election);
		result.addObject("hasVoted", hasVoted);
		result.addObject("daysCelebration", daysCelebration);
		result.addObject("daysCandidature", daysCandidature);
		result.addObject("participatingCandidatures", participatingCandidatures);
		result.addObject("countryFlag", countryFlag);

		return result;

	}
	// Ancillary methods ---------------------------------------------
	private int getDayDifference(Date date1, Date date2) {
		int ans;
		final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date1 = sdf.parse(sdf.format(date1));
		} catch (final ParseException e1) {
			//nada de nada
		}
		try {
			date2 = sdf.parse(sdf.format(date2));
		} catch (final ParseException e1) {
			//nada de nada
		}
		final Long ms = date2.getTime() - date1.getTime();
		if (ms > 0)
			ans = (int) Math.floor(ms / 1000 / 60 / 60 / 24);
		else
			ans = (int) Math.floor(ms / 1000 / 60 / 60 / 24);
		return ans;
	}
}
