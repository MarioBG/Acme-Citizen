
package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidateService;
import services.CandidatureService;
import services.CitizenService;
import services.ElectionService;
import domain.Candidature;
import domain.Election;

@Controller
@RequestMapping("/candidature")
public class CandidatureController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private CandidatureService	candidatureService;

	@Autowired
	private ElectionService		electionService;

	@Autowired
	private CandidateService	candidateService;

	@Autowired
	private CitizenService		citizenService;


	// Constructors --------------------------------------------------

	public CandidatureController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list(@RequestParam final int electionId) {

		final ModelAndView result;
		Collection<Candidature> candidatures;
		Election election;

		candidatures = this.candidatureService.findByElectionId(electionId);
		election = this.electionService.findOne(electionId);

		Date date;
		try {
			final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			date = sdf.parse(sdf.format(new Date()));
		} catch (final Throwable oops) {
			date = new Date();
		}

		Boolean hasVoted = null;
		Date dateOneDayBeforeCelebrationDate = null;
		Collection<Candidature> participatingCandidatures = new ArrayList<Candidature>();

		if (this.citizenService.findByPrincipal() != null) {

			if (this.citizenService.findByPrincipal().getElections().contains(this.electionService.findOne(electionId)))
				hasVoted = true;
			else
				hasVoted = false;

			dateOneDayBeforeCelebrationDate = new DateTime(this.electionService.findOne(electionId).getCelebrationDate()).minusDays(1).toDate();
			participatingCandidatures = this.candidatureService.findByCitizenId(this.citizenService.findByPrincipal().getId());
		}

		result = new ModelAndView("candidature/list");
		result.addObject("candidatures", candidatures);
		result.addObject("election", election);
		result.addObject("date", date);
		result.addObject("hasVoted", hasVoted);
		result.addObject("dateOneDayBeforeCelebrationDate", dateOneDayBeforeCelebrationDate);
		result.addObject("participatingCandidatures", participatingCandidatures);
		result.addObject("requestURI", "candidature/list.do");

		return result;
	}

	// Display -------------------------------------------------------

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam final int candidatureId) {

		final Candidature candidature = this.candidatureService.findOne(candidatureId);

		Boolean isCandidate = null;
		Integer daysCelebration = null;

		if (this.citizenService.findByPrincipal() != null) {

			if (this.candidateService.findByPrincipalAndCandidatureId(candidatureId) != null)
				isCandidate = true;
			else
				isCandidate = false;

			daysCelebration = this.getDayDifference(candidature.getElection().getCelebrationDate(), new Date());
		}

		final ModelAndView result = new ModelAndView("candidature/display");
		result.addObject("candidature", candidature);
		result.addObject("isCandidate", isCandidate);
		result.addObject("daysCelebration", daysCelebration);

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
