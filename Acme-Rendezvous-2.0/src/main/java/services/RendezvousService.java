
package services;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RendezvousRepository;
import domain.Announcement;
import domain.Comment;
import domain.GpsCoordinate;
import domain.Petition;
import domain.Request;
import domain.Citizen;
import forms.RendezvousForm;

@Service
@Transactional
public class RendezvousService {

	// Managed repository -----------------------------------------------------

	@Autowired
	private RendezvousRepository	rendezvousRepository;

	// Supporting services ----------------------------------------------------

	@Autowired
	private UserService				userService;

	@Autowired
	private AdminService			adminService;

	@Autowired
	private AnnouncementService		announcementService;

	@Autowired
	private CommentService			commentService;

	//	@Autowired
	//	private QuestionService			questionService;

	@Autowired
	private Validator				validator;


	// Constructors -----------------------------------------------------------

	public RendezvousService() {
		super();
	}

	// Simple CRUD methods ----------------------------------------------------

	public Petition create() {

		final Petition result = new Petition();

		result.setAdult(false);
		result.setFinalVersion(false);
		result.setDeleted(false);
		result.setOrganiser(this.userService.findByPrincipal());
		result.setAnnouncements(new ArrayList<Announcement>());
		result.setComments(new ArrayList<Comment>());
		//result.setQuestions(new ArrayList<Question>());
		result.setAttendants(new ArrayList<Citizen>());
		result.getAttendants().add(this.userService.findByPrincipal());
		result.setLinkedRendezvouses(new ArrayList<Petition>());
		result.setRequests(new ArrayList<Request>());

		return result;

	}

	public Collection<Petition> findAll() {
		Collection<Petition> res;
		res = this.rendezvousRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Petition findOne(final int rendezvousId) {

		final Petition result = this.rendezvousRepository.findOne(rendezvousId);
		return result;
	}

	public Petition findOneToEdit(final int rendezvousId) {

		final Petition result = this.findOne(rendezvousId);
		this.checkPrincipal(result);
		return result;
	}

	public Petition save(final Petition rendezvous) {

		Assert.notNull(rendezvous);
		Assert.notNull(rendezvous.getName());
		Assert.notNull(rendezvous.getDescription());
		Assert.notNull(rendezvous.getMoment());
		final Date actualDate = new Date();
		Assert.isTrue(rendezvous.getMoment().after(actualDate));
		this.checkPrincipal(rendezvous);

		final Petition result;

		result = this.rendezvousRepository.save(rendezvous);

		final Citizen principal = this.userService.findByPrincipal();

		if (rendezvous.getId() == 0)
			principal.getOrganisedRendezvouses().add(result);
		principal.getRsvpdRendezvouses().add(result);

		return result;
	}

	public void delete(final Petition rendezvous) {

		Assert.notNull(rendezvous);
		Assert.isTrue(rendezvous.getId() != 0);
		Assert.isTrue(this.adminService.findByPrincipal() != null);

		rendezvous.getOrganiser().getOrganisedRendezvouses().remove(rendezvous);
		for (final Citizen attendant : rendezvous.getAttendants())
			attendant.getRsvpdRendezvouses().remove(rendezvous);
		for (final Announcement announcement : rendezvous.getAnnouncements())
			this.announcementService.delete(announcement);
		if (rendezvous.getComments().size() > 0)
			this.commentService.deleteAll(rendezvous.getComments());
		//		for (final Question question : rendezvous.getQuestions())
		//			this.questionService.delete(question);
		for (final Petition linkedRendezvous : rendezvous.getLinkedRendezvouses())
			rendezvous.getLinkedRendezvouses().remove(linkedRendezvous);
		for (final Petition parentRendezvous : this.findParentRendezvouses(rendezvous.getId()))
			parentRendezvous.getLinkedRendezvouses().remove(rendezvous);

		this.rendezvousRepository.delete(rendezvous);
	}
	

	// Other business methods -------------------------------------------------

	public RendezvousForm construct(final Petition rendezvous) {

		Assert.notNull(rendezvous);

		RendezvousForm rendezvousForm;

		rendezvousForm = new RendezvousForm();

		rendezvousForm.setId(rendezvous.getId());
		rendezvousForm.setName(rendezvous.getName());
		rendezvousForm.setDescription(rendezvous.getDescription());
		rendezvousForm.setPicture(rendezvous.getPicture());
		rendezvousForm.setMoment(rendezvous.getMoment());
		if (rendezvous.getGpsCoordinate() == null) {
			rendezvousForm.setLatitude(null);
			rendezvousForm.setLongitude(null);
		} else {
			rendezvousForm.setLatitude(rendezvous.getGpsCoordinate().getLatitude());
			rendezvousForm.setLongitude(rendezvous.getGpsCoordinate().getLongitude());
		}
		rendezvousForm.setAdult(rendezvous.getAdult());
		rendezvousForm.setFinalVersion(rendezvous.getFinalVersion());
		rendezvousForm.setDeleted(rendezvous.getDeleted());

		return rendezvousForm;
	}

	public Petition reconstruct(final RendezvousForm rendezvousForm, final BindingResult binding) {

		Assert.notNull(rendezvousForm);

		Petition rendezvous;

		if (rendezvousForm.getId() != 0)
			rendezvous = this.findOne(rendezvousForm.getId());
		else
			rendezvous = this.create();

		final GpsCoordinate gpsCoordinate = new GpsCoordinate();
		gpsCoordinate.setLatitude(rendezvousForm.getLatitude());
		gpsCoordinate.setLongitude(rendezvousForm.getLongitude());

		rendezvous.setName(rendezvousForm.getName());
		rendezvous.setDescription(rendezvousForm.getDescription());
		rendezvous.setPicture(rendezvousForm.getPicture());
		rendezvous.setMoment(rendezvousForm.getMoment());
		rendezvous.setGpsCoordinate(gpsCoordinate);
		rendezvous.setAdult(rendezvousForm.getAdult());
		rendezvous.setFinalVersion(rendezvousForm.getFinalVersion());
		rendezvous.setDeleted(rendezvousForm.getDeleted());

		if (binding != null)
			this.validator.validate(rendezvous, binding);

		return rendezvous;
	}

	public void checkPrincipal(final Petition rendezvous) {

		Assert.notNull(rendezvous);

		final Citizen principal = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getOrganiser().equals(principal));
	}

	public void checkPrincipalForm(final RendezvousForm rendezvousForm) {

		Assert.notNull(rendezvousForm);

		final Petition rendezvous = this.reconstruct(rendezvousForm, null);
		final Citizen principal = this.userService.findByPrincipal();

		Assert.isTrue(rendezvous.getOrganiser().equals(principal));
	}

	public void changeToDeleted(final int rendezvousId) {

		final Petition rendezvous = this.findOneToEdit(rendezvousId);

		Assert.isTrue(rendezvous.getDeleted() == false);
		Assert.isTrue(rendezvous.getFinalVersion() == false);

		rendezvous.setDeleted(true);
		this.save(rendezvous);
	}

	public Collection<Petition> findFutureMomentAndNotAdult() {

		final Collection<Petition> result = this.rendezvousRepository.findFutureMomentAndNotAdult();
		return result;
	}

	public Collection<Petition> findFutureMoment() {

		final Collection<Petition> result = this.rendezvousRepository.findFutureMoment();
		return result;
	}

	public Collection<Petition> linkedRendezvousesFutureMomentByRendezvousId(final int rendezvousId) {

		final Collection<Petition> result = this.rendezvousRepository.linkedRendezvousesFutureMomentByRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Petition> linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(final int rendezvousId) {

		final Collection<Petition> result = this.rendezvousRepository.linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Petition> findByOrganiserId(final int organiserId) {

		final Collection<Petition> result = this.rendezvousRepository.findByOrganiserId(organiserId);
		return result;
	}

	public Collection<Petition> findByOrganiserIdNotAdult(final int organiserId) {

		final Collection<Petition> result = this.rendezvousRepository.findByOrganiserIdNotAdult(organiserId);
		return result;
	}

	public Collection<Petition> findByAttendantId(final int attendantId) {

		final Collection<Petition> result = this.rendezvousRepository.findByAttendantId(attendantId);
		return result;
	}

	public Collection<Petition> findByAttendantIdNotAdult(final int attendantId) {

		final Collection<Petition> result = this.rendezvousRepository.findByAttendantIdNotAdult(attendantId);
		return result;
	}

	public Collection<Petition> findOrganisedRendezvousesByPrincipal() {

		final Citizen organiser = this.userService.findByPrincipal();
		final Collection<Petition> result = this.findByOrganiserId(organiser.getId());
		return result;
	}

	public Collection<Petition> findRspvdRendezvousesByPrincipal() {

		final Citizen attendant = this.userService.findByPrincipal();
		final Collection<Petition> result = this.findByAttendantId(attendant.getId());
		return result;
	}

	public Collection<Petition> findParentRendezvouses(final int rendezvousId) {

		final Collection<Petition> result = this.rendezvousRepository.findParentRendezvouses(rendezvousId);
		return result;
	}
	
	public Collection<Petition> rendezvousGroupedByCategory(int categoryId){
		final Collection<Petition> result = this.rendezvousRepository.rendezvousGroupedByCategory(categoryId);
		return result;
	}
	
	public void flush() {
		this.rendezvousRepository.flush();
	}
	
	public void YesRSVP(final int rendezvousId) {
		Citizen usuario;
		Petition rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();

		if (rendezvous.getAdult() == true)
			Assert.isTrue(this.Age(usuario.getBirth()) > 17);

		if (!rendezvous.getAttendants().contains(usuario))
			rendezvous.getAttendants().add(usuario);
		this.rendezvousRepository.save(rendezvous);

	}

	public void NoRSVP(final int rendezvousId) {
		Citizen usuario;
		Petition rendezvous;
		rendezvous = this.rendezvousRepository.findOne(rendezvousId);
		usuario = this.userService.findByPrincipal();
		Assert.isTrue(rendezvous.getAttendants().contains(usuario));
		rendezvous.getAttendants().remove(usuario);
		this.rendezvousRepository.save(rendezvous);

	}
	
	public int Age(final Date birthDay) {
		Calendar today, fechan;
		today = Calendar.getInstance();
		fechan = Calendar.getInstance();
		fechan.setTime(birthDay);

		int diffYear = today.get(Calendar.YEAR) - fechan.get(Calendar.YEAR);
		final int diffMonth = today.get(Calendar.MONTH) - fechan.get(Calendar.MONTH);
		final int diffDay = today.get(Calendar.DAY_OF_MONTH) - fechan.get(Calendar.DAY_OF_MONTH);

		if (diffMonth < 0 || (diffMonth == 0 && diffDay < 0))
			diffYear = diffYear - 1;
		return diffYear;

	}
	
	public Petition FakeDelete(Petition rendezvous){
		Citizen user;
		Petition res;
		
		Assert.notNull(rendezvous);
		
		user= this.userService.findByPrincipal();
		Assert.isTrue(user.getOrganisedRendezvouses().contains(rendezvous));
		boolean x= true;
		Assert.isTrue(rendezvous.getFinalVersion()==true);
		rendezvous.setDeleted(x);
		res= this.rendezvousRepository.save(rendezvous);
		
		return res;
	}
	
	

}
