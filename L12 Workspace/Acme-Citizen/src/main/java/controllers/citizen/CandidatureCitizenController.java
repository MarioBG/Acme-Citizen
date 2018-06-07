
package controllers.citizen;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidateService;
import services.CandidatureService;
import services.CitizenService;
import controllers.AbstractController;
import domain.Candidature;
import domain.Election;
import forms.CandidatureForm;

@Controller
@RequestMapping("/candidature/citizen")
public class CandidatureCitizenController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private CandidatureService	candidatureService;

	@Autowired
	private CandidateService	candidateService;

	@Autowired
	private CitizenService		citizenService;


	// Constructors --------------------------------------------------

	public CandidatureCitizenController() {
		super();
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int electionId) {

		final Candidature candidature = this.candidatureService.create(electionId);
		final CandidatureForm candidatureForm = this.candidatureService.construct(candidature);

		final ModelAndView result = this.createEditModelAndView(candidatureForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int candidatureId) {

		ModelAndView result;
		final Candidature candidature = this.candidatureService.findOne(candidatureId);
		final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date date;
		try {
			date = sdf.parse(sdf.format(new Date()));
			Assert.isTrue((candidature.getElection().getCandidatureDate().before(date) || candidature.getElection().getCandidatureDate().equals(date)) && (candidature.getElection().getCelebrationDate().after(date)));
			final CandidatureForm candidatureForm = this.candidatureService.construct(candidature);
			result = this.createEditModelAndView(candidatureForm);
		} catch (final ParseException e) {
			result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CandidatureForm candidatureForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(candidatureForm);
		else
			try {
				final Candidature candidature = this.candidatureService.reconstruct(candidatureForm, binding);
				this.candidatureService.save(candidature);
				result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidatureForm.getElectionId());
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(candidatureForm, "candidature.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int candidatureId) {

		ModelAndView result;
		Candidature candidature = null;

		try {
			candidature = this.candidatureService.findOne(candidatureId);
			Assert.isTrue(candidature.getCandidates().contains(this.candidateService.findByPrincipalAndCandidatureId(candidatureId)));
			this.candidatureService.delete(candidature);
			result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		}

		return result;
	}

	// Vote -------------------------------------------------------------------

	@RequestMapping(value = "/vote", method = RequestMethod.GET)
	public ModelAndView vote(@RequestParam final int candidatureId) {

		ModelAndView result;
		Candidature candidature = null;
		Election election = null;

		try {
			candidature = this.candidatureService.findOne(candidatureId);
			election = candidature.getElection();
			Assert.isTrue(!this.citizenService.findByPrincipal().getElections().contains(election));
			this.candidatureService.vote(candidature);
			result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		} catch (final Throwable oops) {
			result = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		}

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final CandidatureForm candidatureForm) {

		ModelAndView result;

		result = this.createEditModelAndView(candidatureForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final CandidatureForm candidatureForm, final String messageCode) {

		ModelAndView result;

		if (candidatureForm.getId() == 0)
			result = new ModelAndView("candidature/create");
		else
			result = new ModelAndView("candidature/edit");
		result.addObject("candidatureForm", candidatureForm);
		result.addObject("message", messageCode);

		return result;
	}

}
