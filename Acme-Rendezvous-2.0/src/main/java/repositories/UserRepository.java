package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Citizen;

@Repository
public interface UserRepository extends JpaRepository<Citizen, Integer>{
	
	@Query("select u from User u where u.userAccount.id = ?1")
	Citizen findUserByUserAccountId(int uA);
	
	@Query("select r.attendants from Rendezvous r where r.id = ?1")
	Collection<Citizen> findAttendsByRendezvousId(int idRendezvous);
	
	@Query("select r.organiser from Rendezvous r where r.id = ?1")
	Citizen findOrganiserByRendezvousId(int idRendezvous);
	
}
