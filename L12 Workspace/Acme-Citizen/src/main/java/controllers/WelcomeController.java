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
import java.util.Collections;
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
		ArrayList<Chirp> chirps1;
		String welcomeMessage;
		name = "anonymous user";

		chirps1 = (ArrayList<Chirp>) this.chirpService.findAll();
		Collections.sort(chirps1);
		ArrayList<Chirp> chirps2;
		if (chirps1.size() > 3)
			chirps2 = new ArrayList<Chirp>(chirps1.subList(0, 3));
		else
			chirps2 = new ArrayList<Chirp>(chirps1);

		if (this.actorService.findByPrincipal() != null)
			name = this.actorService.findByPrincipal().getName();
		if (this.welcomeMessageService.getWelcomeMessageForLocale(language) != null)
			welcomeMessage = this.welcomeMessageService.getWelcomeMessageForLocale(language);
		else if (this.welcomeMessageService.getWelcomeMessageForLocale("en") != null)
			welcomeMessage = this.welcomeMessageService.getWelcomeMessageForLocale("en");
		else
			welcomeMessage = "Undefined welcome message";

		result = new ModelAndView("welcome/index");
		result.addObject("name", name);
		result.addObject("chirps", chirps2);
		result.addObject("welcomeMessage", welcomeMessage);
		result.addObject("moment", new Date());

		return result;
	}
}
