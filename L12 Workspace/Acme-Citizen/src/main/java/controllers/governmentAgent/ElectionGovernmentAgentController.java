
package controllers.governmentAgent;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ElectionService;
import services.GovernmentAgentService;
import controllers.AbstractController;
import domain.Election;
import forms.ElectionForm;

@Controller
@RequestMapping("/election/governmentAgent")
public class ElectionGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ElectionService			electionService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors ---------------------------------------------------------

	public ElectionGovernmentAgentController() {
		super();
	}

	//Create ---------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		final Election election = this.electionService.create();
		final ElectionForm electionForm = this.electionService.construct(election);

		final ModelAndView result = this.createEditModelAndView(electionForm);

		return result;
	}

	// Edition ----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ElectionForm electionForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(electionForm);
		else
			try {
				final Election election = this.electionService.reconstruct(electionForm, binding);
				this.electionService.save(election);
				result = new ModelAndView("redirect:../list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(electionForm, "election.commit.error");
			}
		return result;
	}

	//Ancillary methods ------------------------

	protected ModelAndView createEditModelAndView(final ElectionForm electionForm) {
		ModelAndView result;

		result = this.createEditModelAndView(electionForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ElectionForm electionForm, final String message) {

		ModelAndView result;

		result = new ModelAndView("election/create");

		result.addObject("electionForm", electionForm);
		result.addObject("message", message);

		return result;
	}

}
