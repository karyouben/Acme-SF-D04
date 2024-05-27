
package acme.features.authenticated.risk;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.risks.Risk;

@Repository
public interface AuthenticatedRiskRepository extends AbstractRepository {

	@Query("select r from Risk r")
	Collection<Risk> findAllRisks();

	@Query("select r from Risk r where r.id = :riskId")
	Risk findOneRiskById(int riskId);

	@Query("select p from Project p where p.id = :id and p.draftMode = false")
	Project findProjectById(int id);

	@Query("select p from Project p WHERE p.draftMode = false")
	Collection<Project> findAllProjects();

}
