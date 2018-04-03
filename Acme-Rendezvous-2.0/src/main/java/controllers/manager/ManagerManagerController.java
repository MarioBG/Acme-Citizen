package controllers.manager;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import controllers.AbstractController;
import domain.Manager;
import forms.ManagerForm;

@Controller
@RequestMapping("/manager/manager")
public class ManagerManagerController extends AbstractController {

	// Services ---------------

	@Autowired
	private ManagerService managerService;

	// Constructors -----------

	public ManagerManagerController() {
		super();
	}

	// Edition ----------------

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {

		Assert.isTrue(managerService.checkAuthority());

		Manager manager = managerService.findByPrincipal();
		ManagerForm managerForm = managerService.construct(manager);

		ModelAndView result = createEditModelAndView(managerForm);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid ManagerForm managerForm,
			BindingResult binding) {

		Assert.isTrue(managerService.checkAuthority());

		ModelAndView result;
		Manager manager = managerService.reconstruct(managerForm, binding);

		if (binding.hasErrors()) {
			result = createEditModelAndView(managerForm, "manager.params.error");
		}else{
			try{
				managerService.save(manager);
				result = new ModelAndView("redirect:/welcome/index.do");
			}catch(Throwable oops){
				result = createEditModelAndView(managerForm, "manager.commit.error");
			}
		}
		
		return result;

	}

	// Ancillary methods ----------------------

	protected ModelAndView createEditModelAndView(ManagerForm managerForm) {

		ModelAndView res;

		res = this.createEditModelAndView(managerForm, null);

		return res;
	}

	protected ModelAndView createEditModelAndView(
			final ManagerForm managerForm, final String message) {
		
		ModelAndView res;

		res = new ModelAndView("manager/edit");
		res.addObject("managerForm", managerForm);
		res.addObject("message", message);
		res.addObject("actionURI", "manager/manager/edit.do");

		return res;
	}

}
