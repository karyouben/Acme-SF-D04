
package acme.features.authenticated.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.objective.Objective;
import acme.entities.project.Project;

@Repository
public interface AuthenticatedObjectiveRepository extends AbstractRepository {

	@Query("select o from Objective o")
	Collection<Objective> findAllObjectives();

	@Query("select o from Objective o where o.id = :objectiveId")
	Objective findOneObjectiveById(int objectiveId);

	@Query("select p from Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

}
