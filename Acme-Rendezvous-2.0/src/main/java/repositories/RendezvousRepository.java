package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Petition;

@Repository
public interface RendezvousRepository extends JpaRepository<Petition, Integer> {

	@Query("select r from Rendezvous r where r.moment > current_timestamp")
	Collection<Petition> findFutureMoment();
	
	@Query("select r from Rendezvous r where r.adult = false and r.moment > current_timestamp")
	Collection<Petition> findFutureMomentAndNotAdult();
	
	@Query("select l from Rendezvous r join r.linkedRendezvouses l where r.id = ?1 and l.moment > current_timestamp")
	Collection<Petition> linkedRendezvousesFutureMomentByRendezvousId(int rendezvousId);
	
	@Query("select l from Rendezvous r join r.linkedRendezvouses l where r.id = ?1 and l.adult = false and l.moment > current_timestamp")
	Collection<Petition> linkedRendezvousesFutureMomentAndNotAdultByRendezvousId(int rendezvousId);

	@Query("select u.organisedRendezvouses from User u where u.id = ?1")
	Collection<Petition> findByOrganiserId(int organiserId);
	
	@Query("select r from User u join u.organisedRendezvouses r where u.id = ?1 and r.adult = false")
	Collection<Petition> findByOrganiserIdNotAdult(int organiserId);
	
	@Query("select u.rsvpdRendezvouses from User u where u.id = ?1")
	Collection<Petition> findByAttendantId(int attendantId);
	
	@Query("select r from User u join u.rsvpdRendezvouses r where u.id = ?1 and r.adult = false")
	Collection<Petition> findByAttendantIdNotAdult(int attendantId);
	
	@Query("select r from Rendezvous r join r.linkedRendezvouses l where l.id=?1")
	Collection<Petition> findParentRendezvouses(int rendezvousId);

	@Query("select r.rendezvous from Request r join r.service.category c where c.id=?1")
	Collection<Petition> rendezvousGroupedByCategory(int categoryId);
	

}
