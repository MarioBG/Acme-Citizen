
package controllers.admin;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.RendezvousService;
import controllers.AbstractController;
import domain.Petition;

@Controller
@RequestMapping("/rendezvous/admin")
public class RendezvousAdminController extends AbstractController {

	// Services ------------------------------------------------------

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private AdminService		adminService;


	// Constructors --------------------------------------------------

	public RendezvousAdminController() {
		super();
	}

	// Listing	------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {

		Assert.notNull(this.adminService.findByPrincipal());

		final Collection<Petition> rendezvouses = this.rendezvousService.findAll();

		final ModelAndView result = new ModelAndView("rendezvous/list");
		result.addObject("rendezvouses", rendezvouses);
		result.addObject("rendezvousSource", null);
		result.addObject("requestURI", "rendezvous/admin/list.do");

		return result;
	}

	// Delete	------------------------------------------------------

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(final int rendezvousId) {

		Assert.notNull(this.adminService.findByPrincipal());

		final Petition rendezvous = this.rendezvousService.findOne(rendezvousId);

		this.rendezvousService.delete(rendezvous);

		return new ModelAndView("redirect:/welcome/index.do");
	}

}
