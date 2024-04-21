
package acme.features.any.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;

@Repository
public interface AnyProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.draftMode = true")
	Collection<Project> findAllPublishedProjects();

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

}
