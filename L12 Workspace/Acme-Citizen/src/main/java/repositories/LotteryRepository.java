package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.Lottery;

public interface LotteryRepository extends JpaRepository<Lottery, Integer> {

}
