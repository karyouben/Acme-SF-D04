
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Sponsorship;

@Repository
public interface AnySponsorshipRepository extends AbstractRepository {

	@Query("select p from Sponsorship p where p.draftMode = false")
	Collection<Sponsorship> findPublishedSponsorships();

	@Query("select p from Sponsorship p where p.id = :id")
	Sponsorship findSponsorshipById(int id);

}
