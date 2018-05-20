
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Candidate;

@Repository
public interface CandidateRepository extends JpaRepository<Candidate, Integer> {

}
