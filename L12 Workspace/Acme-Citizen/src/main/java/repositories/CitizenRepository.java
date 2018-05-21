
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Citizen;

@Repository
public interface CitizenRepository extends JpaRepository<Citizen, Integer> {

	@Query("select c from Citizen c where c.userAccount.id=?1")
	Citizen findCitizenByUserAccountId(int uA);

}
