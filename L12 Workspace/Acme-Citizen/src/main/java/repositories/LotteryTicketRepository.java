
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.LotteryTicket;

@Repository
public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Integer> {

}
