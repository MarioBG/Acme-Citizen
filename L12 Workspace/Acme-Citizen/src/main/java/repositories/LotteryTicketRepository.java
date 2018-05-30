
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LotteryTicket;

@Repository
public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Integer> {

	@Query("Select l from LotteryTicket l where l.citizen.id = ?1")
	Collection<LotteryTicket> findAllByCitizenId(int id);

}
