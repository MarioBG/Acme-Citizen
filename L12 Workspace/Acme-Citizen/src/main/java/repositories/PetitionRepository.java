
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Petition;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer> {

	@Query("select p from Petition p where p.deleted = false")
	Collection<Petition> findNonDeleted();

	@Query("select p from Petition p where p.deleted = true")
	Collection<Petition> findDeleted();

	@Query("select p from Petition p where p.citizen.id = ?1 and p.deleted = false")
	Collection<Petition> findByCitizenIdNonDeleted(int citizenId);

	@Query("select p from Petition p where p.citizen.id = ?1")
	Collection<Petition> findByCitizenId(int citizenId);
}
