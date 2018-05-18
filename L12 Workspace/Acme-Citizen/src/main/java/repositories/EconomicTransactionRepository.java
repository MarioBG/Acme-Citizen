package repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import domain.EconomicTransaction;

public interface EconomicTransactionRepository extends JpaRepository<EconomicTransaction, Integer> {

}
