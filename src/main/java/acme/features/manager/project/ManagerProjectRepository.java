
package acme.features.manager.project;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Assignment;
import acme.entities.project.Project;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Manager;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("SELECT p FROM Project p WHERE p.manager.userAccount.id = :id")
	Collection<Project> findAllProjectsByManagerId(int id);

	@Query("SELECT p FROM Project p WHERE p.id = :id")
	Project findProjectById(int id);

	@Query("SELECT a FROM Assignment a WHERE a.project.id = :id")
	Collection<Assignment> findAssignmentsByProjectId(int id);

	@Query("SELECT m FROM Manager m WHERE m.id = :id")
	Manager findManagerById(int id);

	@Query("SELECT p FROM Project p")
	Collection<Project> findAllProjects();

	@Query("SELECT s FROM SystemConfiguration s")
	List<SystemConfiguration> findSystemConfiguration();

}
