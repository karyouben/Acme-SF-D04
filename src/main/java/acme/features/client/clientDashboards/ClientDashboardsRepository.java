
package acme.features.client.clientDashboards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.client.Client;

@Repository
public interface ClientDashboardsRepository extends AbstractRepository {

	@Query("SELECT c FROM Client c WHERE c.userAccount.id = :id")
	Client findClientById(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 0 and 24.9")
	int totalNumProgressLogLessThan25(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 25 and 49.9")
	int totalNumProgressLogLessBetween25And50(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness between 50 and 74.9")
	int totalNumProgressLogLessBetween50And75(int id);

	@Query("SELECT count(p) FROM Progress p WHERE p.contract.client.userAccount.id = :id and p.completeness > 75")
	int totalNumProgressLogAbove75(int id);

	@Query("select avg(c.budget) FROM Contract c WHERE c.client.userAccount.id = :id")
	Double findAverageContractBudget(int id);

	@Query("select stddev(c.budget) FROM Contract c WHERE c.client.userAccount.id = :id")
	Double findDeviationContractBudget(int id);

	@Query("select max(c.budget) FROM Contract c WHERE c.client.userAccount.id = :id")
	Double findMaximumContractBudget(int id);

	@Query("select min(c.budget) FROM Contract c WHERE c.client.userAccount.id = :id")
	Double findMinimumContractBudget(int id);

}
