
package controllers.governmentAgent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidatureService;
import controllers.AbstractController;
import domain.Candidature;

@Controller
@RequestMapping("/candidature/governmentAgent")
public class CandidatureGovernmentAgentController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private CandidatureService	candidatureService;


	// Constructors ---------------------------------------------------------

	public CandidatureGovernmentAgentController() {
		super();
	}

	// Delete  ----------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam final int candidatureId) {

		ModelAndView res = null;
		final Candidature candidature = this.candidatureService.findOne(candidatureId);

		try {
			this.candidatureService.delete(candidature);
			res = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		} catch (final Throwable oops) {
			res = new ModelAndView("redirect:../../election/display.do?electionId=" + candidature.getElection().getId());
		}

		return res;
	}
}
