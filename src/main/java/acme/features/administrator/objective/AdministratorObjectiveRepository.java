
package acme.features.administrator.objective;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AdministratorObjectiveRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id and p.draftMode = false")
	Project findProjectById(int id);

	@Query("select p from Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

}
