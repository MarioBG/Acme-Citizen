
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Candidate;
import domain.Candidature;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	@Query("select c from Candidature c join c.candidates cand where cand.citizen.id = ?1")
	Collection<Candidature> findByCitizenId(int id);

}
