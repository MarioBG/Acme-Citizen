
package controllers.governmentAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.PetitionService;
import controllers.AbstractController;
import domain.Petition;

@Controller
@RequestMapping("/petition/governmentAgent")
public class PetitionGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private PetitionService	petitionService;


	// Constructors ---------------------------------------------------------

	public PetitionGovernmentAgentController() {
		super();
	}

	// Delete  ----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int petitionId) {

		ModelAndView res;
		Petition petition = null;

		try {
			petition = this.petitionService.findOne(petitionId);
			this.petitionService.delete(petition);
			res = new ModelAndView("redirect:../list.do?citizenId=" + petition.getCitizen().getId());
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:../list.do?citizenId=" + petition.getCitizen().getId());
		}

		return res;
	}
}
