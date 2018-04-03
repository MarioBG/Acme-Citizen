
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;
import domain.Citizen;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
	
	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findActorByUserAccountId(int uA);
	
	@Query("select a from Rendezvous r join r.announcements a where r.id = ?1")
	Actor findAnnouncementsByRendezvous(int idRendezvous);
	
	@Query("select u.rsvpdRendezvouses from User u")
	Collection<Citizen> findUserRsvpdRendezvous();
	
}
