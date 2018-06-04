
package controllers.governmentAgent;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.GovernmentAgentService;
import controllers.AbstractController;
import domain.Candidature;
import domain.Citizen;
import domain.Election;
import domain.GovernmentAgent;
import domain.Petition;
import forms.GovernmentAgentForm;

@Controller
@RequestMapping("/governmentAgent/governmentAgent")
public class GovernmentAgentGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors ---------------------------------------------------------

	public GovernmentAgentGovernmentAgentController() {
		super();
	}

	// Register

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		final GovernmentAgent agent = this.governmentAgentService.create();
		final GovernmentAgentForm governmentAgentForm = this.governmentAgentService.construct(agent);

		res = this.createEditModelAndView(governmentAgentForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final GovernmentAgentForm governmentAgentForm, final BindingResult binding) {
		ModelAndView res;
		GovernmentAgent govAgent;

		if (binding.hasErrors())
			res = this.createEditModelAndView(governmentAgentForm, "user.params.error");
		else if (!governmentAgentForm.getRepeatPassword().equals(governmentAgentForm.getPassword()))
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorPassword");
		else if (governmentAgentForm.getTermsAndConditions() == false)
			res = this.createEditModelAndView(governmentAgentForm, "user.params.errorTerms");
		else if (!this.governmentAgentService.findByPrincipal().getCanCreateMoney() && governmentAgentForm.getCanCreateMoney())
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorNoCanCreateMoney");
		else if (!this.governmentAgentService.findByPrincipal().getCanOrganiseElection() && governmentAgentForm.getCanOrganiseElection())
			res = this.createEditModelAndView(governmentAgentForm, "user.commit.errorNoCanOrganiseElection");
		else
			try {
				govAgent = this.governmentAgentService.reconstruct(governmentAgentForm, binding);
				this.governmentAgentService.save(govAgent);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				if (oops.getMessage() == null)
					res = this.createEditModelAndView(governmentAgentForm, "user.commit.error");
				else
					res = this.createEditModelAndView(governmentAgentForm, oops.getMessage());
			}

		return res;
	}
	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		result = new ModelAndView("governmentAgent/dashboard");

		Double numberRegisteredActors;
		Double[] avgMinMaxStdvPerCitizen;
		final Double[] avgMinMaxStdvPerGovAgent;
		Collection<Petition> petitionsByComments;
		Collection<Election> electionsByComments;
		Double percentageElectionCandidates;
		Collection<Citizen> citizensMoreLotteryTicketsAverage;
		Collection<Candidature> candidaturesMoreVotesAverage;
		Double[] avgMinMaxStdvVotesPerElection;
		Double[] avgMinMaxStdvCandidaturesPerElection;
		Double allMoneyInSystem;
		Double[] avgMinMaxStdvMoneyPerActor;

		petitionsByComments = this.governmentAgentService.getTop3PetitionsByCommentSize();
		numberRegisteredActors = this.governmentAgentService.numberRegisteredActors();
		avgMinMaxStdvPerGovAgent = this.governmentAgentService.computeAvgMinMaxStdvPerGovAgent();
		avgMinMaxStdvPerCitizen = this.governmentAgentService.computeAvgMinMaxStdvPerCitizen();
		petitionsByComments = this.governmentAgentService.getPetitionsByComments();
		electionsByComments = this.governmentAgentService.getElectionsByComments();
		percentageElectionCandidates = this.governmentAgentService.getPercentageElectionCandidates();
		citizensMoreLotteryTicketsAverage = this.governmentAgentService.citizensMoreLotteryTicketsAverage();
		candidaturesMoreVotesAverage = this.governmentAgentService.candidaturesMoreVotesAverage();
		avgMinMaxStdvVotesPerElection = this.governmentAgentService.computeAvgMinMaxStdvVotesPerElection();
		avgMinMaxStdvCandidaturesPerElection = this.governmentAgentService.computeAvgMinMaxStdvCandidaturesPerElection();
		allMoneyInSystem = this.governmentAgentService.getAllMoneyInSystem();
		avgMinMaxStdvMoneyPerActor = this.governmentAgentService.computeAvgMinMaxStdvMoneyPerActor();

		result.addObject("petitionsByComments", petitionsByComments);
		result.addObject("numberRegisteredActors", numberRegisteredActors);
		result.addObject("avgMinMaxStdvPerGovAgent", avgMinMaxStdvPerGovAgent);
		result.addObject("avgMinMaxStdvPerCitizen", avgMinMaxStdvPerCitizen);
		result.addObject("petitionsByComments", petitionsByComments);
		result.addObject("electionsByComments", electionsByComments);
		result.addObject("percentageElectionCandidates", percentageElectionCandidates);
		result.addObject("citizensMoreLotteryTicketsAverage", citizensMoreLotteryTicketsAverage);
		result.addObject("candidaturesMoreVotesAverage", candidaturesMoreVotesAverage);
		result.addObject("avgMinMaxStdvVotesPerElection", avgMinMaxStdvVotesPerElection);
		result.addObject("avgMinMaxStdvCandidaturesPerElection", avgMinMaxStdvCandidaturesPerElection);
		result.addObject("allMoneyInSystem", allMoneyInSystem);
		result.addObject("avgMinMaxStdvMoneyPerActor", avgMinMaxStdvMoneyPerActor);

		return result;
	}
	// Ancillary methods ---------------------------------------------------------------

	protected ModelAndView createEditModelAndView(final GovernmentAgentForm govAgentForm) {
		ModelAndView result;

		result = this.createEditModelAndView(govAgentForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final GovernmentAgentForm govAgentForm, final String message) {
		ModelAndView result;

		final GovernmentAgent gov = this.governmentAgentService.findByPrincipal();
		final Boolean canCreateMoney = gov.getCanCreateMoney();
		final Boolean canElection = gov.getCanOrganiseElection();

		result = new ModelAndView("governmentAgent/register");
		result.addObject("governmentAgentForm", govAgentForm);
		result.addObject("message", message);
		result.addObject("canCreateMoneyParent", canCreateMoney);
		result.addObject("canOrganiseElectionParent", canElection);

		return result;
	}

}
