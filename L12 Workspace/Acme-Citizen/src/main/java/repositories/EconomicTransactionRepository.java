
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.EconomicTransaction;

@Repository
public interface EconomicTransactionRepository extends JpaRepository<EconomicTransaction, Integer> {

}
