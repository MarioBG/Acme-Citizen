package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.AnnouncementRepository;
import domain.Announcement;
import domain.Petition;
import domain.Citizen;
import forms.AnnouncementForm;

@Service
@Transactional
public class AnnouncementService {

	// Managed repository
	
	@Autowired
	private AnnouncementRepository announcementRepository;
	
	// Supporting services
//	@Autowired
//	private RendezvousService rendezvousService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private AdminService adminService;

	@Autowired
	private Validator validator;
	
	// Constructors
	
	public AnnouncementService(){
		super();
	}
	
	// Simple CRUD methods 
	
	public Announcement create(final Petition rendezvous){
		Announcement res = new Announcement();
		
		Date moment = new Date(System.currentTimeMillis()-1);
		
		res.setRendezvous(rendezvous);
		res.setMoment(moment);
		
		return res;
		
	}
	
	public Collection<Announcement> findAll(){
		Collection<Announcement> res;
		res = new ArrayList<Announcement>();
		
		res = this.announcementRepository.findAll();
		
		Assert.notNull(res);
		return res;
	}
	
	
	public Announcement findOne(int announcementId){
		Assert.isTrue(announcementId!=0);
		Announcement res;
		
		res = this.announcementRepository.findOne(announcementId);
		
		Assert.notNull(res);
		return res;
	}
	
	public Announcement save(Announcement announcement){
		Assert.notNull(announcement);
		this.checkPrincipal(announcement);
		
		Announcement res;
		
		res = this.announcementRepository.save(announcement);
		
		Assert.notNull(res);
		return res;
		
	}
	
	public void delete(Announcement announcement){
		this.adminService.checkAuthority();
		
		Assert.notNull(announcement);
		Assert.isTrue(announcement.getId()!=0);
		Assert.isTrue(this.announcementRepository.exists(announcement.getId()));

		
		this.announcementRepository.delete(announcement);
	}
	
	// Other busines methods
	
	public void checkPrincipal(Announcement announcement){
		Citizen principal = userService.findByPrincipal();
		Assert.isTrue(principal.equals(announcement.getRendezvous().getOrganiser()));
	}
	
	public Collection<Announcement> findAnnouncementsByRendezvous(int rendezvousId){
		Collection<Announcement> res = new ArrayList<Announcement>();
		
		res = announcementRepository.findAnnouncementsByRendezvous(rendezvousId);
		Assert.notNull(res);
		
		return res;
	}
	
	public AnnouncementForm construct(Announcement announcement){
		AnnouncementForm res = new AnnouncementForm();
		
		res.setId(announcement.getId());
		res.setTitle(announcement.getTitle());
		res.setDescription(announcement.getDescription());
		res.setRendezvous(announcement.getRendezvous());
		
		return res;
	}
	
	public Announcement reconstruct(AnnouncementForm announcementForm, BindingResult binding){
		Assert.notNull(announcementForm);
		Announcement res = new Announcement();
		
		Date moment = new Date(System.currentTimeMillis()-1);
		
		res.setId(announcementForm.getId());
		res.setMoment(moment);
		res.setTitle(announcementForm.getTitle());
		res.setDescription(announcementForm.getDescription());
		res.setRendezvous(announcementForm.getRendezvous());
		
		if(binding!=null)
			validator.validate(res, binding);
		
		return res;
	}
	
	public Collection<Announcement> findAnnouncementsByAttendants(){
		Collection<Announcement> res;
		Citizen user = new Citizen();
		user = this.userService.findByPrincipal();
		res = this.announcementRepository.findAnnouncementsByAttendants(user.getId());
		Assert.notNull(res);
		return res;
	}
	
	public void flush(){
		this.announcementRepository.flush();
	}


}
