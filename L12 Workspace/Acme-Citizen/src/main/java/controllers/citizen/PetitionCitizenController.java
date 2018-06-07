
package controllers.citizen;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CitizenService;
import services.PetitionService;
import controllers.AbstractController;
import domain.Petition;
import forms.PetitionForm;

@Controller
@RequestMapping("/petition/citizen")
public class PetitionCitizenController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private PetitionService	petitionService;

	@Autowired
	private CitizenService	citizenService;


	// Constructors --------------------------------------------------

	public PetitionCitizenController() {
		super();
	}

	// Listing -------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Collection<Petition> petitions;

		petitions = this.petitionService.findByPrincipal();

		final ModelAndView result = new ModelAndView("petition/list");
		result.addObject("petitions", petitions);
		result.addObject("requestURI", "petition/citizen/list.do");

		return result;
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		final Petition petition = this.petitionService.create();
		final PetitionForm petitionForm = this.petitionService.construct(petition);

		final ModelAndView result = this.createEditModelAndView(petitionForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam final int petitionId) {

		final Petition petition = this.petitionService.findOne(petitionId);
		Assert.isTrue(petition.getCitizen() == this.citizenService.findByPrincipal());
		final PetitionForm petitionForm = this.petitionService.construct(petition);

		final ModelAndView result = this.createEditModelAndView(petitionForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final PetitionForm petitionForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(petitionForm);
		else
			try {
				final Petition petition = this.petitionService.reconstruct(petitionForm, binding);
				this.petitionService.save(petition);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(petitionForm, "petition.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final PetitionForm petitionForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(petitionForm);
		else
			try {
				final Petition petition = this.petitionService.reconstruct(petitionForm, binding);
				this.petitionService.virtualDelete(petition);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(petitionForm, "petition.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/addGovernmentAgent", method = RequestMethod.GET)
	public ModelAndView addGovernmentAgent(@RequestParam final int petitionId, @RequestParam final int governmentAgentId) {

		ModelAndView result;

		this.petitionService.addGovernmentAgent(petitionId, governmentAgentId);

		result = new ModelAndView("redirect:../../governmentAgent/citizen/addGovernmentAgents.do?petitionId=" + petitionId);

		return result;
	}

	@RequestMapping(value = "/removeGovernmentAgent", method = RequestMethod.GET)
	public ModelAndView removeGovernmentAgent(@RequestParam final int petitionId, @RequestParam final int governmentAgentId) {

		ModelAndView result;

		this.petitionService.removeGovernmentAgent(petitionId, governmentAgentId);

		result = new ModelAndView("redirect:../../governmentAgent/citizen/addGovernmentAgents.do?petitionId=" + petitionId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

	protected ModelAndView createEditModelAndView(final PetitionForm petitionForm) {

		ModelAndView result;

		result = this.createEditModelAndView(petitionForm, null);
		return result;
	}

	protected ModelAndView createEditModelAndView(final PetitionForm petitionForm, final String messageCode) {

		ModelAndView result;

		if (petitionForm.getId() == 0)
			result = new ModelAndView("petition/create");
		else
			result = new ModelAndView("petition/edit");
		result.addObject("petitionForm", petitionForm);
		result.addObject("message", messageCode);

		return result;
	}

}
