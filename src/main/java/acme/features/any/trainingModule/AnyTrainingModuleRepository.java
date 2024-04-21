
package acme.features.any.trainingModule;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.trainingModule.TrainingModule;

@Repository
public interface AnyTrainingModuleRepository extends AbstractRepository {

	@Query("SELECT t FROM TrainingModule t WHERE t.draftMode = false")
	Collection<TrainingModule> findAllPublishedTrainingModules();

	@Query("SELECT t FROM TrainingModule t WHERE t.id = :id")
	TrainingModule findTrainingModuleById(int id);

}
