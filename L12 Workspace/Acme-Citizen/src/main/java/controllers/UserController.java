
package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.UserService;
import domain.User;
import forms.CitizenForm;

@Controller
@RequestMapping("/user")
public class UserController extends AbstractController {

	// Services -------------------------------------------------------------

	@Autowired
	private UserService	userService;


	// Constructors ---------------------------------------------------------

	public UserController() {
		super();
	}

	// Listing --------------------------------------------------------------

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		
		ModelAndView result;
		Collection<User> users;
		User principal;

		users = userService.findAll();
		principal = userService.findByPrincipal();
		if(principal != null){
			users.remove(principal);
		}

		result = new ModelAndView("user/list");
		result.addObject("users", users);
		result.addObject("principal", principal);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam int userId) {
		ModelAndView result;
		User user;

		user = userService.findOne(userId);

		result = new ModelAndView("user/display");
		result.addObject("user", user);
		result.addObject("requestURI", "user/display.do");

		return result;
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;

		User user = userService.create();
		CitizenForm citizenForm = userService.construct(user);;
		res = this.createEditModelAndView(citizenForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final CitizenForm citizenForm, final BindingResult binding) {
		ModelAndView res;
		User user;

		if (binding.hasErrors())
			res = this.createEditModelAndView(citizenForm, "user.params.error");
		else if (!citizenForm.getRepeatPassword().equals(citizenForm.getPassword()))
			res = this.createEditModelAndView(citizenForm, "user.commit.errorPassword");
		else if (citizenForm.getTermsAndConditions() == false) {
			res = this.createEditModelAndView(citizenForm, "user.params.errorTerms");
		} else
			try {
				user = userService.reconstruct(citizenForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(citizenForm, "user.commit.error");
			}

		return res;
	}

	// Ancillary methods ---------------------------------------------------------------
	
	protected ModelAndView createEditModelAndView(final CitizenForm citizenForm) {
		ModelAndView result;

		result = this.createEditModelAndView(citizenForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final CitizenForm citizenForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("userForm", citizenForm);
		result.addObject("message", message);

		return result;
	}

}
