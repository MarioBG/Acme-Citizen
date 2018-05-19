
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.GovernmentAgent;

@Repository
public interface GovernmentAgentRepository extends JpaRepository<GovernmentAgent, Integer> {

	@Query("select a from GovernmentAgent a where a.userAccount.id=?1")
	GovernmentAgent findGovernmentAgentByUserAccountId(int uA);

}
