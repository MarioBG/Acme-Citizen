
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Petition;

@Repository
public interface PetitionRepository extends JpaRepository<Petition, Integer> {

}
