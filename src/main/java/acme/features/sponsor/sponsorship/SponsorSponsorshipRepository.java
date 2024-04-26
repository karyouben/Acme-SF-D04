
package acme.features.sponsor.sponsorship;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.project.Project;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Repository
public interface SponsorSponsorshipRepository extends AbstractRepository {

	@Query("select p from Sponsorship p where p.sponsor.userAccount.id = :id")
	Collection<Sponsorship> findSponsorshipsBySponsorId(int id);

	@Query("select p from Sponsorship p where p.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select pus from Invoice pus where pus.sponsorship = :sponsorship")
	Collection<Invoice> findInvoicesBySponsorship(Sponsorship sponsorship);

	@Query("select m from Sponsor m where m.id = :id")
	Sponsor findOneSponsorById(int id);

	@Query("select p from Sponsorship p where p.code = :code")
	Sponsorship findSponsorshipByCode(String code);

	@Query("select p from Project p ")
	Collection<Project> findAllPublishedProjects();

	@Query("select p from Project p where p.id = :id")
	Project findProjectbyId(int id);
}
