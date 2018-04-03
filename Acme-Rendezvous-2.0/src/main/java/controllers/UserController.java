
package controllers;

import java.util.ArrayList;
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
import domain.Citizen;
import forms.UserForm;

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
		Collection<Citizen> users;

		users = userService.findAll();

		result = new ModelAndView("user/list");
		result.addObject("user", users);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	@RequestMapping(value = "creator/list", method = RequestMethod.GET)
	public ModelAndView listCreator(@RequestParam int organiserId) {
		ModelAndView result;
		Citizen organizer;

		organizer = userService.findOne(organiserId);

		result = new ModelAndView("user/list");
		result.addObject("user", organizer);
		result.addObject("requestURI", "user/creator/list.do");

		return result;
	}

	@RequestMapping(value = "display", method = RequestMethod.GET)
	public ModelAndView listUser(@RequestParam int userId) {
		ModelAndView result;
		Citizen user;

		user = userService.findOne(userId);

		result = new ModelAndView("user/list");
		result.addObject("user", user);
		result.addObject("requestURI", "user/list.do");

		return result;
	}

	// Editing ---------------------------------------------------------------

	@RequestMapping(value = "/user/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;
		Citizen user;
		UserForm editUserForm;

		user = this.userService.findByPrincipal();
		editUserForm = this.userService.construct(user);
		
		result = this.createEditModelAndViewEdit(editUserForm);

		return result;
	}

	@RequestMapping(value = "/user/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView editSave(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView res;
		if (binding.hasErrors())
			res = this.createEditModelAndViewEdit(userForm, "user.params.error");
		else
			try {
				Citizen user = this.userService.reconstruct(userForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndViewEdit(userForm, "user.commit.error");
			}
		System.out.println(binding);

		return res;
	}

	// Registering ----------------------------------------------------------

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView res;
		UserForm userForm;

		userForm = new UserForm();
		res = this.createEditModelAndView(userForm);

		return res;
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final UserForm userForm, final BindingResult binding) {
		ModelAndView res;
		Citizen user;

		if (binding.hasErrors())
			res = this.createEditModelAndView(userForm, "user.params.error");
		else if (!userForm.getRepeatPassword().equals(userForm.getPassword()))
			res = this.createEditModelAndView(userForm, "user.commit.errorPassword");
		else if (userForm.getTermsAndConditions() == false) {
			res = this.createEditModelAndView(userForm, "user.params.errorTerms");
		} else
			try {
				user = userService.reconstruct(userForm, binding);
				this.userService.save(user);
				res = new ModelAndView("redirect:../");
			} catch (final Throwable oops) {
				res = this.createEditModelAndView(userForm, "user.commit.error");
			}

		return res;
	}

	@RequestMapping(value = "/rendezvousCreator/list", method = RequestMethod.GET)
	public ModelAndView listRendezvousCreator(@RequestParam int rendezvousId) {
		ModelAndView result;
		Citizen organizer;

		organizer = userService.findOrganiserByRendezvousId(rendezvousId);

		result = new ModelAndView("user/list");
		result.addObject("user", organizer);
		result.addObject("requestURI", "user/rendezvousCreator/list.do");

		return result;
	}

	@RequestMapping(value = "/listRendezvousAttends", method = RequestMethod.GET)
	public ModelAndView listRendezvousAttendant(@RequestParam int rendezvousId) {
		ModelAndView result;
		Collection<Citizen> attends = new ArrayList<Citizen>();

		attends = this.userService.findAttendsByRendezvousId(rendezvousId);

		result = new ModelAndView("user/list");
		result.addObject("user", attends);
		result.addObject("requestURI", "user/listRendezvousAttends.do");

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndView(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndView(final UserForm userForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/register");
		result.addObject("userForm", userForm);
		result.addObject("message", message);

		return result;
	}
	
	protected ModelAndView createEditModelAndViewEdit(final UserForm userForm) {
		ModelAndView result;

		result = this.createEditModelAndViewEdit(userForm, null);

		return result;
	}

	protected ModelAndView createEditModelAndViewEdit(final UserForm userForm, final String message) {
		ModelAndView result;

		result = new ModelAndView("user/edit");
		result.addObject("userForm", userForm);
		result.addObject("message", message);

		return result;
	}
}
