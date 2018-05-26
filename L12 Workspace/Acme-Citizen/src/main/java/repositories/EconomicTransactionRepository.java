
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.EconomicTransaction;

@Repository
public interface EconomicTransactionRepository extends JpaRepository<EconomicTransaction, Integer> {

	@Query("select e from EconomicTransaction e where e.creditor.actor.id = ?1")
	Collection<EconomicTransaction> findCreditorTransactionByActorId(int actorId);

	@Query("select e from EconomicTransaction e where e.debtor.actor.id = ?1")
	Collection<EconomicTransaction> findDebtorTransactionByActorId(int actorId);

}
