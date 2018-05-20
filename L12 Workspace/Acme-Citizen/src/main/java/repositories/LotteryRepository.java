
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Lottery;

@Repository
public interface LotteryRepository extends JpaRepository<Lottery, Integer> {

}
