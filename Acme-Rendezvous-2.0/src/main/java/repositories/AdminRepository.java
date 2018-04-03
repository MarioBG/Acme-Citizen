package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Admin;
import domain.Manager;
import domain.Service;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Integer> {

	@Query("select a from Admin a where a.userAccount.id=?1")
	Admin findAdministratorByUserAccountId(int uA);

	// C-1
	@Query("select avg(u.organisedRendezvouses.size), sqrt(sum(u.organisedRendezvouses.size*u.organisedRendezvouses.size)/count(u.organisedRendezvouses.size)-(avg(u.organisedRendezvouses.size)*avg(u.organisedRendezvouses.size))) from User u")
	Object[] avgSqtrUser();

	// C-2
	@Query("select (select count(u) from User u where u.organisedRendezvouses.size!=0)* 1.0/count(u) from User u where u.organisedRendezvouses.size= 0) ")
	Double ratioUserRendezvous();

	// C-3
	@Query("select avg(r.attendants.size), sqrt(sum(r.attendants.size*r.attendants.size)/count(r.attendants.size)-(avg(r.attendants.size)*avg(r.attendants.size))) from Rendezvous r")
	Object[] avgSqtrUserPerRendezvous();

	// C-4
	@Query("select avg(u.rsvpdRendezvouses.size), sqrt(sum(u.rsvpdRendezvouses.size*u.rsvpdRendezvouses.size)/count(u.rsvpdRendezvouses.size)-(avg(u.rsvpdRendezvouses.size)*avg(u.rsvpdRendezvouses.size))) from User u")
	Object[] avgSqtrUser2();

	// C-5
	@Query("select r from Rendezvous r where r.attendants.size !=0 order by r.attendants.size desc")
	Collection<Object> topRendezvous();

	// B-1
	@Query("select avg(r.announcements.size), sqrt(sum(r.announcements.size*r.announcements.size)/count(r.announcements.size)-(avg(r.announcements.size)*avg(r.announcements.size))) from Rendezvous r")
	Object[] avgSqtrAnnouncementPerRendezvous();

	// B-2
	@Query("select r from Rendezvous r where r.announcements.size> (select 0.75* avg(r.announcements.size) from Rendezvous r)")
	Collection<Object> rendezvousNumberAnnouncements();

	// B-3
	@Query("select r from Rendezvous r where r.linkedRendezvouses.size > (select avg(r.linkedRendezvouses.size)* 1.1 from Rendezvous r)")
	Collection<Object> rendezvousLinked();

	// // A-1
	// @Query("select avg(r.questions.size), sqrt(sum(r.questions.size*r.questions.size)/count(r.questions.size)-(avg(r.questions.size)*avg(r.questions.size))) from Rendezvous r")
	// Object[] avgSqtrQuestionsPerRendezvous();
	//
	// // A-2
	// @Query("select (select count(a) from Answer a where a.question.rendezvous.id= r.id) from Rendezvous r")
	// Collection<Object> avgSqtrAnswersPerRendezvous();

	// A-3
	@Query("select avg(c.replies.size), sqrt(sum(c.replies.size*c.replies.size)/count(c.replies.size)-(avg(c.replies.size)*avg(c.replies.size))) from Comment c")
	Object[] avgSqtRrepliesPerComment();

	// Nuevas queries entregable D09

	// C1 The best-selling services.
	@Query("select s, s.requests.size from Service s order by s.requests.size desc")
	Collection<Object> bestSellingServices();

	// C2 The managers who provide more services than the average.
	@Query("select m from Manager m where m.services.size > (select avg(p.services.size) from Manager p)")
	Collection<Object> managersMoreServicesThanAvg();
	
	//C3 The managers who have got more services cancelled.
	@Query("select m, m.services.size from Manager m join m.services s where s.cancelled = true order by m.services.size desc")
	Collection<Manager> managersMoreServicesCancelled();
	
	//B1 The average number of categories per rendezvous
	@Query("select avg(r.requests.size) from Rendezvous r")
	Double AvgCategoryPerRendezvous();
	
	//B2 The average ratio of services in each category.
	@Query("select avg(r.services.size) from Category r")
	Double AvgServicesPerCategories();
	
	//B3 The average, the minimum, the maximum, and the standard deviation of services requested per rendezvous.
	@Query("select avg(r.requests.size), min(r.requests.size), max(r.requests.size),stddev(r.requests.size) from Rendezvous r")
	Object[] AvgMinMaxStrServicePerRendezvous();
	
	//B4 The top-selling services.
	@Query("select c.services from Category c order by c.services.size desc")
	Collection<domain.Service> TopSellingServices();
}
