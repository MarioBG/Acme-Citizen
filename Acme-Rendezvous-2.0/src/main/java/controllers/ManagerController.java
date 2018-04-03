package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import services.ManagerService;
import domain.Manager;
import forms.ManagerForm;

@Controller
@RequestMapping("/manager")
public class ManagerController extends AbstractController{

	// Services -----------------------
	@Autowired
	private ManagerService managerService;
	
	// Constructors --------------------
	public ManagerController(){
		super();
	}
	
	// Register ------------------------
	@RequestMapping(value="/register", method = RequestMethod.GET)
	public ModelAndView create(){
		ModelAndView res;
		
		ManagerForm managerForm = new ManagerForm();
		res = this.createEditModelAndView(managerForm);
		
		return res;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final ManagerForm managerForm, final BindingResult binding) {
		ModelAndView res;
		Manager manager;

		if (binding.hasErrors())
			res = this.createEditModelAndView(managerForm, "manager.params.error");
		else if (!managerForm.getRepeatPassword().equals(managerForm.getPassword()))
			res = this.createEditModelAndView(managerForm, "manager.commit.errorPassword");
		else if (managerForm.getTermsAndConditions() == false) {
			res = this.createEditModelAndView(managerForm, "manager.params.errorTerms");
		} else
			try {
				manager = managerService.reconstruct(managerForm, binding);
				this.managerService.save(manager);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				System.out.println(oops);
				System.out.println(binding);
				res = this.createEditModelAndView(managerForm, "manager.commit.error");
			}

		return res;
	}

	
	
	protected ModelAndView createEditModelAndView(final ManagerForm managerForm) {
		ModelAndView res;
		
		res = this.createEditModelAndView(managerForm,null);
		
		return res;
	}

	protected ModelAndView createEditModelAndView(final ManagerForm managerForm,
			final String message) {
		ModelAndView res;
		
		res = new ModelAndView("manager/register");
		res.addObject("managerForm",managerForm);
		res.addObject("message",message);
		res.addObject("actionURI", "manager/register.do");
		
		return res;
	}
	
	
}
