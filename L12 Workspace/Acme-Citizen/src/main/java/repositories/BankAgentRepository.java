
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.BankAgent;

@Repository
public interface BankAgentRepository extends JpaRepository<BankAgent, Integer> {

	@Query("select a from BankAgent a where a.userAccount.id=?1")
	BankAgent findBankAgentByUserAccountId(int uA);

}
