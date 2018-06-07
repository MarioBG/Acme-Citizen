
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

	@Query("select c from Candidate c where c.candidature.id = ?1")
	Collection<Candidate> findByCandidatureId(int candidatureId);

	@Query("select c from Candidate c where c.citizen.id = ?1 and c.candidature.id = ?2")
	Candidate findByCitizenIdAndCandidatureId(int citizenId, int candidatureId);

}
