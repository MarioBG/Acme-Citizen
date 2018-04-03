
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.ServiceRepository;
import domain.Category;
import domain.Manager;
import domain.Petition;
import domain.Request;
import domain.Service;
import forms.ServiceForm;

@org.springframework.stereotype.Service
@Transactional
public class ServiceService {

	// Managed repository

	@Autowired
	private ServiceRepository	serviceRepository;

	// Supported services

	@Autowired
	private RendezvousService	rendezvousService;

	@Autowired
	private ManagerService		managerService;

	@Autowired
	private AdminService		adminService;

	@Autowired
	private CategoryService		categoryService;

	@Autowired
	private Validator			validator;


	//	@Autowired
	//	private CategoryService		categoryService;

	// Constructors

	public ServiceService() {
		super();
	}

	// Simple CRUD methods

	public Service create() {
		Assert.isTrue(this.managerService.checkAuthority());
		final Service res = new Service();
		final Manager manager = this.managerService.findByPrincipal();
		final boolean cancelled = false;
		final Collection<Request> requests = new ArrayList<Request>();
		final Category category= new Category();

		res.setManager(manager);
		res.setCancelled(cancelled);
		res.setRequests(requests);
		res.setCategory(category);

		return res;
	}

	public Collection<Service> findAll() {
		Collection<Service> res;
		res = this.serviceRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Service findOne(final int serviceId) {
		Assert.isTrue(serviceId != 0);
		Service res;
		res = this.serviceRepository.findOne(serviceId);
		return res;
	}

	public Service findOneToEdit(final int serviceId) {
		Assert.isTrue(serviceId != 0);
		Service res;
		res = this.serviceRepository.findOne(serviceId);
		Assert.isTrue(res.getManager().equals(managerService.findByPrincipal()));
		Assert.isTrue(res.isCancelled() == false);
		return res;
	}

	public Service save(final Service service) {

		Assert.isTrue(service.getManager().equals(this.managerService.findByPrincipal()) || this.adminService.checkAuthority());
		Assert.notNull(service.getManager());

		Service res;

		res = this.serviceRepository.save(service);
		if (service.getId() == 0)
			service.getManager().getServices().add(res);

		if (service.getCategory() != null && !service.getCategory().getServices().contains(res)) {
			service.getCategory().getServices().add(res);
		}

		return res;
	}

	public void delete(final Service service) {

		Assert.isTrue(service.getManager().equals(this.managerService.findByPrincipal()));
		Assert.isTrue(service.getRequests().isEmpty());

		service.getManager().getServices().remove(service);
		if (service.getCategory() != null) {
			service.getCategory().getServices().remove(service);
			categoryService.save(service.getCategory());
		}

		this.serviceRepository.delete(service);
	}

	// Other business methods

	public Collection<Service> findByRendezvousId(int rendezvousId) {
		Collection<Service> result = serviceRepository.findByRendezvousId(rendezvousId);
		return result;
	}

	public Collection<Service> findByManagerId(int managerId) {
		Collection<Service> result = serviceRepository.findByManagerId(managerId);
		return result;
	}

	public void changeCancelled(int serviceId) {

		Assert.isTrue(adminService.checkAuthority());

		Service service = findOne(serviceId);

		Assert.isTrue(service.isCancelled() == false);
		Assert.isTrue(service.getRequests().isEmpty());

		service.setCancelled(true);
		save(service);
	}

	public Collection<Service> findNonCancelled() {

		Collection<Service> result = serviceRepository.findNonCancelled();
		return result;
	}

	public Collection<Service> findAvalibleServicesByRendezvousId(int rendezvousId) {

		Collection<Service> result = serviceRepository.findNonCancelled();
		Petition rendezvous = rendezvousService.findOne(rendezvousId);
		for (Request request : rendezvous.getRequests()) {
			if (result.contains(request.getService())) {
				result.remove(request.getService());
			}
		}

		return result;
	}

	public ServiceForm construct(Service service) {
		ServiceForm res = new ServiceForm();

		res.setId(service.getId());
		res.setName(service.getName());
		res.setDescription(service.getDescription());
		res.setPicture(service.getPicture());

		return res;
	}

	public Service reconstruct(ServiceForm serviceForm, BindingResult binding) {

		Assert.notNull(serviceForm);

		Service res;

		if (serviceForm.getId() == 0) {
			res = create();
		} else {
			res = findOne(serviceForm.getId());
		}

		res.setName(serviceForm.getName());
		res.setDescription(serviceForm.getDescription());
		res.setPicture(serviceForm.getPicture());
		res.setCategory(serviceForm.getCategory());

		if (binding != null)
			validator.validate(res, binding);

		return res;
	}

	public void flush() {
		this.serviceRepository.flush();
	}

}
