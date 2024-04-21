
package acme.features.developer.developerDashboards;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.roles.Developer;

@Repository
public interface DeveloperDashboardsRepository extends AbstractRepository {

	@Query("SELECT d FROM Developer d WHERE d.userAccount.id = :id")
	Developer findDeveloperById(int id);

	@Query("SELECT count(t) FROM TrainingModule t WHERE t.developer.userAccount.id = :id and t.updateMoment IS NOT NULL and t.draftMode = false")
	int totalTrainingModulesWithUpdateMoment(int id);

	@Query("SELECT count(t) FROM TrainingSession t WHERE t.trainingModule.developer.userAccount.id = :id and t.link IS NOT NULL and t.draftMode = false")
	int totalTrainingSessionsWithLink(int id);

	@Query("select avg(t.totalTime) FROM TrainingModule t WHERE t.developer.userAccount.id = :id and t.draftMode = false")
	Double findAverageTrainingModuleTime(int id);

	@Query("select stddev(t.totalTime) FROM TrainingModule t WHERE t.developer.userAccount.id = :id and t.draftMode = false")
	Double findDeviationTrainingModuleTime(int id);

	@Query("select max(t.totalTime) FROM TrainingModule t WHERE t.developer.userAccount.id = :id and t.draftMode = false")
	Double findMaximumTrainingModuleTime(int id);

	@Query("select min(t.totalTime) FROM TrainingModule t WHERE t.developer.userAccount.id = :id and t.draftMode = false")
	Double findMinimumTrainingModuleTime(int id);

}
