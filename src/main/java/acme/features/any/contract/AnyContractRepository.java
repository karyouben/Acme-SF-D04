
package acme.features.any.contract;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.contract.Contract;

@Repository
public interface AnyContractRepository extends AbstractRepository {

	@Query("SELECT c FROM Contract c WHERE c.draftMode = false")
	Collection<Contract> findAllPublishedContract();

	@Query("SELECT c FROM Contract c WHERE c.id = :id")
	Contract findContractById(int id);

}
