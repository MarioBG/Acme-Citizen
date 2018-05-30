
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Candidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Integer> {

	@Query("select c from Candidature c join c.candidates cand where cand.citizen.id = ?1")
	Collection<Candidature> findByCitizenId(int id);

	@Query("select c from Candidature c where c.election.id = ?1")
	Collection<Candidature> findByElectionId(int electionId);

}
