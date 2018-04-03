
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Service;

@Repository
public interface ServiceRepository extends JpaRepository<Service, Integer> {

	@Query("select s, s.requests.size from Service s order by s.requests.size desc")
	Collection<Service> bestSellingServices();
	
	@Query("select r.service from Request r where r.rendezvous.id = ?1")
	Collection<Service> findByRendezvousId(int rendezvousId);
	
	@Query("select s from Service s where s.manager.id = ?1")
	Collection<Service> findByManagerId(int managerId);
	
	@Query("select s from Service s where s.cancelled = false")
	Collection<Service> findNonCancelled();

}
