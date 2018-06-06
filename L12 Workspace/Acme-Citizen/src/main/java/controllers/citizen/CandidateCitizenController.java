
package controllers.citizen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.CandidateService;
import controllers.AbstractController;
import domain.Candidate;

@Controller
@RequestMapping("/candidate/citizen")
public class CandidateCitizenController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private CandidateService	candidateService;


	// Constructors --------------------------------------------------

	public CandidateCitizenController() {
		super();
	}

	// Create ---------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView register(@RequestParam final int candidatureId) {

		final Candidate candidate = this.candidateService.create(candidatureId);
		this.candidateService.save(candidate);

		final ModelAndView result = new ModelAndView("redirect:../../candidature/display.do?candidatureId=" + candidatureId);

		return result;
	}

	// Ancillary methods ------------------------------------------------------

}
