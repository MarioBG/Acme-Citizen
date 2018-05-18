package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.LotteryTicket;

public interface LotteryTicketRepository extends JpaRepository<LotteryTicket, Integer> {

}
