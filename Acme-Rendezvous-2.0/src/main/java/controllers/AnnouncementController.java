package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.AnnouncementService;
import domain.Announcement;

@Controller
@RequestMapping("/announcement")
public class AnnouncementController extends AbstractController {
	
	// Services ----------------------
	@Autowired
	private AnnouncementService announcementService;
	
	// Constructors ------------------
	public AnnouncementController(){
		super();
	}
	
	// Listing ------------------------
	@RequestMapping(value="/list", method=RequestMethod.GET)
	public ModelAndView list(@RequestParam final int rendezvousId){
		ModelAndView res;
		Collection<Announcement> announcements;
	
		announcements = this.announcementService.findAnnouncementsByRendezvous(rendezvousId);
		
		res = new ModelAndView("announcement/list");
		res.addObject("announcement",announcements);
		res.addObject("requestURI","announcement/list.do");
		
		return res;
	}
	
	
	
	@RequestMapping(value = "/display", method = RequestMethod.GET)
	public ModelAndView display(@RequestParam int announcementId) {
		ModelAndView result;
		Announcement announcement;

		announcement = this.announcementService.findOne(announcementId);

		result = new ModelAndView("announcement/display");
		result.addObject("announcement", announcement);
		result.addObject("requestURI", "announcements/display.do");

		return result;
	}
	
	@RequestMapping(value="/display", method=RequestMethod.POST, params ="delete")
	public ModelAndView create(@Valid final Announcement announcement, 
			final BindingResult binding){
		ModelAndView res;
		
		Assert.notNull(announcement);
		try{
			this.announcementService.delete(announcement);
			res = new ModelAndView("redirect:admin/list.do");
		}catch (final Throwable oops) {
			res = this.createEditModelAndView(announcement, "announcement.commit.error");
		}
		
		return res;
	}

	
//	// Create --------------
//	@RequestMapping(value="/user/create", method=RequestMethod.GET)
//	public ModelAndView create(@RequestParam final int rendezvousId){
//		ModelAndView res;
//		Announcement announcement;
//		Rendezvous rendezvous;
//		
//		
//		rendezvous = this.rendezvousService.findOne(rendezvousId);
//		
//		announcement = this.announcementService.create(rendezvous);
//				
//		announcement.setRendezvous(rendezvous);
//		
//		res = this.createEditModelAndView(announcement);
//		
//		return res;
//	}

	
	
	protected ModelAndView createEditModelAndView(final Announcement announcement) {
		ModelAndView res;
		
		res = this.createEditModelAndView(announcement,null);
		
		return res;
	}

	protected ModelAndView createEditModelAndView(final Announcement announcement,
			final String message) {
		ModelAndView res;
		res = new ModelAndView("announcement/edit");
		
		res.addObject("announcement",announcement);
		res.addObject("message",message);
		
		return res;
	}
}
