
package acme.features.manager.managerDashboards;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Priority;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Manager;

@Repository
public interface ManagerDashboardsRepository extends AbstractRepository {

	@Query("SELECT m FROM Manager m WHERE m.userAccount.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT count(us) FROM UserStory us WHERE us.manager.userAccount.id = :id and us.priority = :p")
	int totalUserStoriesByPriority(int id, Priority p);

	@Query("select avg(us.cost) FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Double findAverageUserStoryCost(int id);

	@Query("select stddev(us.cost) FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Double findDeviationUserStoryCost(int id);

	@Query("select max(us.cost) FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Double findMaximumUserStoryCost(int id);

	@Query("select min(us.cost) FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Double findMinimumUserStoryCost(int id);

	@Query("select avg(p.totalCost.amount) FROM Project p WHERE p.manager.userAccount.id = :id and p.totalCost.currency = :currency")
	Double findAverageProjectCost(int id, String currency);

	@Query("select stddev(p.totalCost.amount) FROM Project p WHERE p.manager.userAccount.id = :id and p.totalCost.currency = :currency")
	Double findDeviationProjectCost(int id, String currency);

	@Query("select max(p.totalCost.amount) FROM Project p WHERE p.manager.userAccount.id = :id and p.totalCost.currency = :currency")
	Double findMaximumProjectCost(int id, String currency);

	@Query("select min(p.totalCost.amount) FROM Project p WHERE p.manager.userAccount.id = :id and p.totalCost.currency = :currency")
	Double findMinimumProjectCost(int id, String currency);

	@Query("SELECT s FROM SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
