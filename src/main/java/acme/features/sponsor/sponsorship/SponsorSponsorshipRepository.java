
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.project.Project;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int id);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select s from Sponsor s where s.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select s from Sponsorship s where s.code = :code")
	Sponsorship findSponsorshipByCode(String code);

	@Query("select p from Project p where p.draftMode = false")
	Collection<Project> findAllUnpublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int id);

	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.sponsorship.id = :id AND i.published = true")
	int countPublishedInvoicesBySponsorshipId(@Param("id") int id);

	@Query("SELECT config FROM SystemConfiguration config")
	SystemConfiguration findSystemConfiguration();
}
