
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Election;

@Repository
public interface ElectionRepository extends JpaRepository<Election, Integer> {

}
