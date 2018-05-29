
package repositories;

import java.util.Collection;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Candidature;
import domain.Citizen;
import domain.Election;
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
	Page<Election> getElectionsByComments(Pageable pageable);

	@Query(value = "select 100*((select count(u1) from Citizen u1 where u1.candidates.size>0)/ count(u2)) from Citizen u2")
	Double getPercentageElectionCandidates();

	@Query("select n from Citizen n group by n having n.lotteryTickets.size > (select avg(n2.lotteryTickets.size)*1.2 from Citizen n2)")
	Collection<Citizen> citizensMoreLotteryTicketsAverage();

	@Query("select n from Candidature n group by n having n.voteNumber > (select avg(n2.voteNumber)*1.2 from Candidature n2)")
	Collection<Candidature> candidaturesMoreVotesAverage();

	@Query("select avg(m.citizens.size),min(m.citizens.size),max(m.citizens.size),sqrt(sum(m.citizens.size*m.citizens.size)/count(m.citizens.size)-(avg(m.citizens.size)*avg(m.citizens.size))) from Election m")
	Double[] computeAvgMinMaxStdvVotesPerElection();

	@Query("select avg(m.candidatures.size),min(m.candidatures.size),max(m.candidatures.size),sqrt(sum(m.candidatures.size*m.candidatures.size)/count(m.candidatures.size)-(avg(m.candidatures.size)*avg(m.candidatures.size))) from Election m")
	Double[] computeAvgMinMaxStdvCandidaturesPerElection();

	@Query("select sum(ba.money) from BankAccount ba")
	Double getAllMoneyInSystem();

	@Query("select min(coalesce(a.money, 0)),max(coalesce(a.money, 0)),avg(coalesce(a.money, 0)),sqrt(sum(coalesce(a.money, 0)*coalesce(a.money, 0))/count(m)-(avg(coalesce(a.money, 0))*avg(coalesce(a.money, 0)))) from Actor m left join m.bankAccount a")
	Double[] computeAvgMinMaxStdvMoneyPerActor();					//TODO NO FUNCIONA CORRECTAMENTE: No coge los 0 de cuando el Actor no tiene cuenta bancaria

}
