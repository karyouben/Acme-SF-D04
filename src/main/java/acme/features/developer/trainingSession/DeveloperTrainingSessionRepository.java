
package acme.features.developer.trainingSession;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingSessionRepository extends AbstractRepository {

	@Query("SELECT a FROM TrainingSession a WHERE a.trainingModule.id = :id")
	Collection<TrainingSession> findTrainingSessionsByTrainingModuleId(int id);

	@Query("SELECT us FROM TrainingSession us WHERE us.id = :id")
	TrainingSession findTrainingSessionById(int id);

	@Query("SELECT t FROM TrainingModule t WHERE t.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("SELECT us FROM TrainingSession us WHERE us.trainingModule.developer.userAccount.id = :id")
	Collection<TrainingSession> findTrainingSessionsByDeveloperId(int id);

	@Query("SELECT d FROM Developer d WHERE d.id = :id")
	Developer findDeveloperById(int id);

	@Query("SELECT t FROM TrainingSession t")
	Collection<TrainingSession> findAllTrainingSessions();

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

}
