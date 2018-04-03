package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.AdminRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Admin;
import domain.Manager;

@Service
@Transactional
public class AdminService {

	// Managed repository

	@Autowired
	private AdminRepository adminRepository;

	// Supporting services

	// Constructors

	public AdminService() {
		super();
	}

	// Simple CRUD methods

	public Admin create() {
		final Admin res = new Admin();

		final UserAccount userAccount = new UserAccount();
		final Authority authority = new Authority();

		authority.setAuthority(Authority.ADMIN);
		userAccount.addAuthority(authority);
		res.setUserAccount(userAccount);

		return res;
	}

	public Collection<Admin> findAll() {
		Collection<Admin> res;
		res = this.adminRepository.findAll();
		Assert.notNull(res);
		return res;
	}

	public Admin findOne(final int administratorId) {
		Assert.isTrue(administratorId != 0);
		Admin res;
		res = this.adminRepository.findOne(administratorId);
		Assert.notNull(res);
		return res;
	}

	public Admin save(final Admin administrator) {
		Admin res;

		if (administrator.getId() == 0) {
			String pass = administrator.getUserAccount().getPassword();

			final Md5PasswordEncoder code = new Md5PasswordEncoder();

			pass = code.encodePassword(pass, null);

			administrator.getUserAccount().setPassword(pass);
		}

		res = this.adminRepository.save(administrator);
		return res;
	}

	// Ancillary methods

	public Admin findByPrincipal() {
		Admin res;
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		res = this.adminRepository.findAdministratorByUserAccountId(userAccount
				.getId());
		Assert.notNull(res);
		return res;
	}

	public boolean checkAuthority() {
		UserAccount userAccount;
		userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Collection<Authority> authority = userAccount.getAuthorities();
		Assert.notNull(authority);
		final Authority res = new Authority();
		res.setAuthority("ADMIN");
		if (authority.contains(res))
			return true;
		else
			return false;
	}
	
	public void flush() {
		this.adminRepository.flush();
	}

	// C-1
	public Object[] avgSqtrUser() {
		Object[] res;
		res = this.adminRepository.avgSqtrUser();
		return res;
	}

	//
	// public void checkAuthority() {
	// UserAccount userAccount;
	// userAccount = LoginService.getPrincipal();
	// Assert.notNull(userAccount);
	// Collection<Authority> authority = userAccount.getAuthorities();
	// Assert.notNull(authority);
	// Authority res = new Authority();
	// res.setAuthority("ADMIN");
	// Assert.isTrue(authority.contains(res));
	// }
	//
	// // C-1
	// public Object[] avgSqtrUser() {
	// Object[] res;
	// res = this.adminRepository.avgSqtrUser();
	// return res;
	// }
	//
	// C-2
	public Double ratioUserRendezvous() {
		Double res;
		res = this.adminRepository.ratioUserRendezvous();
		return res;
	}

	// C-3
	public Object[] avgSqtrUserPerRendezvous() {
		Object[] res;
		res = this.adminRepository.avgSqtrUserPerRendezvous();
		return res;
	}

	// C-4
	public Object[] avgSqtrUser2() {
		Object[] res;
		res = this.adminRepository.avgSqtrUser2();
		return res;
	}

	// C-5
	public Collection<Object> topRendezvous() {
		Collection<Object> res;
		res = this.adminRepository.topRendezvous();
		return res;
	}

	// B-1
	public Object[] avgSqtrAnnouncementPerRendezvous() {
		Object[] res;
		res = this.adminRepository.avgSqtrAnnouncementPerRendezvous();
		return res;
	}

	// B-2
	public Collection<Object> rendezvousNumberAnnouncements() {
		Collection<Object> res;
		res = this.adminRepository.rendezvousNumberAnnouncements();
		return res;
	}

	// B-3
	public Collection<Object> rendezvousLinked() {
		Collection<Object> res;
		res = this.adminRepository.rendezvousLinked();
		return res;
	}

	// // A-1
	// public Object[] avgSqtrQuestionsPerRendezvous() {
	// Object[] res;
	// res = this.adminRepository.avgSqtrQuestionsPerRendezvous();
	// return res;
	// }
	//
	// // A-2
	// public Collection<Object> avgSqtrAnswersPerRendezvous() {
	// Collection<Object> res;
	// res = this.adminRepository.avgSqtrAnswersPerRendezvous();
	// return res;
	// }

	// A-3
	public Object[] avgSqtRrepliesPerComment() {
		Object[] res;
		res = this.adminRepository.avgSqtRrepliesPerComment();
		return res;
	}

	// Nuevas queries

	// C-1
	public Collection<Object> bestSellingService() {
		final Collection<Object> result = this.adminRepository
				.bestSellingServices();
		return result;
	}

	// C-2
	public Collection<Object> managersMoreServicesThanAvg() {
		final Collection<Object> result = this.adminRepository
				.managersMoreServicesThanAvg();
		return result;
	}

	// C-3
	public Collection<Manager> managersMoreServicesCancelled() {
		final Collection<Manager> result = this.adminRepository
				.managersMoreServicesCancelled();
		return result;
	}
	
	// B-1
	public Double AvgCategoryPerRendezvous() {
		final Double result = this.adminRepository
				.AvgCategoryPerRendezvous();
		return result;
	}
	//B-2
	public Double AvgServicesPerCategories() {
		final Double result = this.adminRepository
				.AvgServicesPerCategories();
		return result;
	}
	
	//B-3
	public Object[] AvgMinMaxStrServicePerRendezvous() {
		final Object[] result = this.adminRepository
				.AvgMinMaxStrServicePerRendezvous();
		return result;
	}
	
	//B-4
	public Collection<domain.Service> TopSellingService() {
		final Collection<domain.Service> result = this.adminRepository
				.TopSellingServices();
		return result;
	}
	

}
