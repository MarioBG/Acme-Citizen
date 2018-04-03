package controllers.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AdminService;
import services.ServiceService;
import controllers.AbstractController;

@Controller
@RequestMapping("/service/admin")
public class ServiceAdminController extends AbstractController {

	// Services ---------------
	
	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private AdminService adminService;

	// Constructors -----------
	
	public ServiceAdminController() {
		super();
	}

	// Cancel -----------------
	
	@RequestMapping(value = "/cancel", method = RequestMethod.GET)
	public ModelAndView cancel(@RequestParam int serviceId){
		
		Assert.isTrue(adminService.checkAuthority());
		
		ModelAndView result;
		
		serviceService.changeCancelled(serviceId);
		
		result = new ModelAndView("redirect:/service/list.do");
		
		return result;
	}

}
