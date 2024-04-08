
package acme.features.developer.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Repository
public interface DeveloperTrainingModuleRepository extends AbstractRepository {

	@Query("SELECT t FROM TrainingModule t WHERE t.developer.userAccount.id = :id")
	Collection<TrainingModule> findAllTrainingModulesByDeveloperId(int id);

	@Query("SELECT t FROM TrainingModule t WHERE t.id = :id")
	TrainingModule findTrainingModuleById(int id);

	@Query("SELECT a FROM TrainingSession a WHERE a.trainingModule.id = :id")
	Collection<TrainingSession> findTrainingSessionsByTrainingModuleId(int id);

	@Query("SELECT d FROM Developer d WHERE d.id = :id")
	Developer findDeveloperById(int id);

	@Query("SELECT t FROM TrainingModule t")
	Collection<TrainingModule> findAllTrainingModules();

}
