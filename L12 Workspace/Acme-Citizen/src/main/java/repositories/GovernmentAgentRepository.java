
package repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.GovernmentAgent;
import domain.Petition;

@Repository
public interface GovernmentAgentRepository extends JpaRepository<GovernmentAgent, Integer> {

	@Query("select a from GovernmentAgent a where a.userAccount.id=?1")
	GovernmentAgent findGovernmentAgentByUserAccountId(int uA);

	@Query("select count(a) from Actor a")
	Double numberRegisteredActors();

	@Query("select avg(m.petitions.size),min(m.petitions.size),max(m.petitions.size),sqrt(sum(m.petitions.size*m.petitions.size)/count(m.petitions.size)-(avg(m.petitions.size)*avg(m.petitions.size))) from Citizen m")
	Double[] computeAvgMinMaxStdvPerCitizen();

	@Query("select avg(m.petitions.size),min(m.petitions.size),max(m.petitions.size),sqrt(sum(m.petitions.size*m.petitions.size)/count(m.petitions.size)-(avg(m.petitions.size)*avg(m.petitions.size))) from GovernmentAgent m")
	Double[] computeAvgMinMaxStdvPerGovAgent();

	@Query("select p from Petition p group by p.comments.size")
	Page<Petition> getPetitionsByComments(Pageable pageable);

	@Query("select p from Election p group by p.comments.size")
	Page<Petition> getElectionsByComments(Pageable pageable);

	@Query(value = "select concat(100*(select count(u1) from Citizen u1 where u1.candidates.size>0)/ count(u2), '%') from Citizen u2")
	String getPercentageElectionCandidates();
}
