/*
 * WelcomeController.java
 * 
 * Copyright (C) 2017 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ChirpService;
import services.WelcomeMessageService;
import domain.Actor;
import domain.Chirp;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	// Support services -------------------------------------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private ChirpService			chirpService;

	@Autowired
	private WelcomeMessageService	welcomeMessageService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@CookieValue(value = "language", defaultValue = "es") final String language, @RequestParam(required = false, defaultValue = "John Doe") String name) {
		ModelAndView result;
		ArrayList<Chirp> chirps;
		String welcomeMessage;
		name = "anonymous user";

		chirps = this.chirpService.lastChirps();

		if (this.actorService.findByPrincipal() != null)
			name = this.actorService.findByPrincipal().getName();
		final Actor principal = this.actorService.findByPrincipal();
		if (this.welcomeMessageService.getWelcomeMessageForLocale(language) != null)
			welcomeMessage = this.welcomeMessageService.getWelcomeMessageForLocale(language);
		else if (this.welcomeMessageService.getWelcomeMessageForLocale("en") != null)
			welcomeMessage = this.welcomeMessageService.getWelcomeMessageForLocale("en");
		else
			welcomeMessage = "Undefined welcome message";

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("principal", principal);
		result.addObject("chirps", chirps);
		result.addObject("welcomeMessage", welcomeMessage);
		result.addObject("moment", new Date());

		return result;
	}
}
