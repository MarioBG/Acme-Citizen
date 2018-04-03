package services;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.RequestRepository;
import domain.CreditCard;
import domain.Petition;
import domain.Request;
import forms.RequestForm;

@Service
@Transactional
public class RequestService {

	// Managed repository

	@Autowired
	private RequestRepository requestRepository;

	// Supported services

	@Autowired
	private RendezvousService rendezvousService;

	@Autowired
	private UserService userService;

	@Autowired
	private ServiceService serviceService;
	
	@Autowired
	private Validator				validator;

	// Constructors

	public RequestService() {
		super();
	}

	// Simple CRUD methods

	public Request create(final Integer rendezvousId) {

		final Request result = new Request();
		if (rendezvousId != null) {
			final Petition rendezvous = this.rendezvousService
					.findOneToEdit(rendezvousId);
			result.setRendezvous(rendezvous);
		}

		return result;

	}

	public Collection<Request> findAll() {

		final Collection<Request> result = this.requestRepository.findAll();
		return result;
	}

	public Request findOne(final int requestId) {

		Assert.isTrue(requestId != 0);

		final Request result = this.requestRepository.findOne(requestId);
		return result;
	}

	public Request save(final Request request) {

		Assert.isTrue(this.userService.findByPrincipal()
				.getOrganisedRendezvouses().contains(request.getRendezvous()));
		Assert.notNull(request.getRendezvous());
		Assert.notNull(request.getService());
		Assert.isTrue(checkCreditCard(request.getCreditCard()));

		final Request result = this.requestRepository.save(request);
		if (request.getId() == 0) {
			request.getRendezvous().getRequests().add(result);
//			this.rendezvousService.save(request.getRendezvous());
			request.getService().getRequests().add(result);
//			this.serviceService.save(request.getService());
		}

		return result;
	}

	public void delete(final Request request) {

		Assert.isTrue(this.userService.findByPrincipal()
				.getOrganisedRendezvouses().contains(request.getRendezvous()));

		request.getRendezvous().getRequests().remove(request);
		this.rendezvousService.save(request.getRendezvous());
		request.getService().getRequests().remove(request);
		this.serviceService.save(request.getService());

		this.requestRepository.delete(request);
	}

	// Other business methods
	
	public void flush() {
		this.requestRepository.flush();
	}

	public RequestForm construct(final Request request) {

		Assert.notNull(request);

		RequestForm requestForm;

		requestForm = new RequestForm();

		requestForm.setId(request.getId());
		if(requestForm.getId() != 0){
			requestForm.setHolder(request.getCreditCard().getHolder());
			requestForm.setBrand(request.getCreditCard().getBrand());
			requestForm.setNumber(request.getCreditCard().getNumber());
			requestForm.setExpirationMonth(request.getCreditCard().getExpirationMonth());
			requestForm.setExpirationYear(request.getCreditCard().getExpirationYear());
			requestForm.setCvv(request.getCreditCard().getCvv());
		}
		requestForm.setComment(request.getComment());
		requestForm.setRendezvousId(request.getRendezvous().getId());
		if (request.getId() != 0) {
			requestForm.setServiceId(request.getService().getId());
		} else {
			requestForm.setServiceId(0);
		}

		return requestForm;
	}

	public Request reconstruct(final RequestForm requestForm,
			final BindingResult binding) {

		Assert.notNull(requestForm);

		Request request;

		if (requestForm.getId() != 0)
			request = this.findOne(requestForm.getId());
		else
			request = this.create(requestForm.getRendezvousId());

		final CreditCard creditCard = new CreditCard();
		creditCard.setHolder(requestForm.getHolder());
		creditCard.setBrand(requestForm.getBrand());
		creditCard.setNumber(requestForm.getNumber());
		creditCard.setExpirationMonth(requestForm.getExpirationMonth());
		creditCard.setExpirationYear(requestForm.getExpirationYear());
		creditCard.setCvv(requestForm.getCvv());

		request.setCreditCard(creditCard);
		request.setComment(request.getComment());
		request.setRendezvous(rendezvousService.findOne(requestForm.getRendezvousId()));
		request.setService(serviceService.findOne(requestForm.getServiceId()));

		if (binding != null)
			this.validator.validate(request, binding);

		return request;
	}
	
	private boolean checkCreditCard(final CreditCard creditCard) {
		Assert.notNull(creditCard);
		Assert.notNull(creditCard.getHolder());
		Assert.notNull(creditCard.getBrand());
		Assert.notNull(creditCard.getNumber());
		Assert.notNull(creditCard.getExpirationMonth());
		Assert.notNull(creditCard.getExpirationYear());
		Assert.notNull(creditCard.getCvv());

		boolean result = false;
		Date now = new Date();
		final Calendar cal = Calendar.getInstance();
		cal.setTime(now);
		final int year = cal.get(Calendar.YEAR);
		final int month = cal.get(Calendar.MONTH) + 1;

		if (creditCard.getExpirationYear() > year)
			result = true;
		else if (creditCard.getExpirationYear() == year) {
			if (creditCard.getExpirationMonth() >= month)
				result = true;
			else
				result = false;
		} else
			result = false;

		return result;
	}

}
