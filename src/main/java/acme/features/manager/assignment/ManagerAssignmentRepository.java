
package acme.features.manager.assignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Assignment;
import acme.entities.project.Project;
import acme.entities.project.UserStory;

@Repository
public interface ManagerAssignmentRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.id = :id")
	UserStory findUserStoryById(int id);

	@Query("SELECT us FROM UserStory us WHERE us.manager.userAccount.id = :id")
	Collection<UserStory> findUserStoriesByManagerId(int id);

	@Query("SELECT a FROM Assignment a WHERE a.project.id = :id")
	Collection<Assignment> findAssignmentsByProjectId(int id);

}
