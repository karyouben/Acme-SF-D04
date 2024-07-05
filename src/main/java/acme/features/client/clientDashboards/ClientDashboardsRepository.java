
package acme.features.client.clientDashboards;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.client.Client;

@Repository
public interface ClientDashboardsRepository extends AbstractRepository {

	@Query("SELECT c FROM Client c WHERE c.userAccount.id = :id")
	Client findClientById(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 0 and 24 and p.draftMode = false")
	Optional<Integer> totalNumProgressLogLessThan25(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 25 and 49 and p.draftMode = false")
	Optional<Integer> totalNumProgressLogLessBetween25And50(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 50 and 74 and p.draftMode = false")
	Optional<Integer> totalNumProgressLogLessBetween50And75(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness > 75 and p.draftMode = false")
	Optional<Integer> totalNumProgressLogAbove75(int id);

	@Query("select avg(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id and c.draftMode = false")
	Optional<Double> findAverageContractBudget(int id);

	@Query("select stddev(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id and c.draftMode = false")
	Optional<Double> findDeviationContractBudget(int id);

	@Query("select max(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id and c.draftMode = false")
	Optional<Double> findMaximumContractBudget(int id);

	@Query("select min(c.budget.amount) FROM Contract c WHERE c.client.userAccount.id = :id and c.draftMode = false")
	Optional<Double> findMinimumContractBudget(int id);

}
