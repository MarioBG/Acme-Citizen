
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Candidature;

@Repository
public interface CandidatureRepository extends JpaRepository<Candidature, Integer> {

}
