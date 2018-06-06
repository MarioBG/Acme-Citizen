
package controllers.governmentAgent;

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

import services.ChirpService;
import services.GovernmentAgentService;
import controllers.AbstractController;
import domain.Chirp;
import forms.ChirpForm;

@Controller
@RequestMapping("/chirp/governmentAgent")
public class ChirpGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private GovernmentAgentService	governmentAgentService;


	// Constructors ---------------------------------------------------------

	public ChirpGovernmentAgentController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		ModelAndView result;
		Collection<Chirp> chirps;

		chirps = this.chirpService.findByPrincipal();

		result = new ModelAndView("chirp/list");
		result.addObject("chirps", chirps);
		result.addObject("governmentAgent", null);
		result.addObject("requestURI", "chirp/governmentAgent/list.do");

		return result;
	}

	//Create ---------------------------------

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		final Chirp chirp = this.chirpService.create();
		final ChirpForm chirpForm = this.chirpService.construct(chirp);

		final ModelAndView result = this.createEditModelAndView(chirpForm);

		return result;
	}

	// Edition ----------------------------------------------------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam final int chirpId) {

		Assert.notNull(this.governmentAgentService.findByPrincipal());

		final Chirp chirp = this.chirpService.findOne(chirpId);
		Assert.isTrue(chirp.getGovernmentAgent().equals(this.governmentAgentService.findByPrincipal()));
		final ChirpForm chirpForm = this.chirpService.construct(chirp);

		final ModelAndView result = this.createEditModelAndView(chirpForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ChirpForm chirpForm, final BindingResult binding) {

		ModelAndView result;
		if (binding.hasErrors())
			result = this.createEditModelAndView(chirpForm);
		else
			try {
				final Chirp chirp = this.chirpService.reconstruct(chirpForm, binding);
				this.chirpService.save(chirp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				result = this.createEditModelAndView(chirpForm, "chirp.commit.error");
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "delete")
	public ModelAndView delete(final ChirpForm chirpForm, final BindingResult binding) {

		ModelAndView result;
		try {
			final Chirp chirp = this.chirpService.reconstruct(chirpForm, binding);
			this.chirpService.delete(chirp);
			result = new ModelAndView("redirect:list.do");
		} catch (final Throwable oops) {
			result = this.createEditModelAndView(chirpForm, "chirp.commit.error");
		}

		return result;
	}

	//Ancillary methods ------------------------

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm) {
		ModelAndView result;

		result = this.createEditModelAndView(chirpForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final ChirpForm chirpForm, final String message) {

		ModelAndView result;

		if (chirpForm.getId() == 0)
			result = new ModelAndView("chirp/create");
		else
			result = new ModelAndView("chirp/edit");

		result.addObject("chirpForm", chirpForm);
		result.addObject("message", message);

		return result;
	}

}
