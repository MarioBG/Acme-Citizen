
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Lottery;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {

	@Query("select l from Lottery l where l.celebrationDate > CURRENT_DATE")
	Collection<Lottery> findAllAfterToday();

	@Query("select l from Lottery l where l.governmentAgent.id = ?1")
	Collection<Lottery> getLotteryByGovernmentAgentId(int id);

	@Query("select l from Lottery l where l.governmentAgent.id = ?1")
	Collection<Lottery> getLotteryWinner(int id);

}
