
package acme.features.sponsor.invoice;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.sponsorships.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.entities.systemconf.SystemConfiguration;

@Repository
public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findInvoiceBySponsorshipId(int id);

	@Query("select i from Invoice i where i.sponsorship.sponsor.id = :id")
	Collection<Invoice> findAllInvoicesBySponsorId(int id);

	@Query("select i from Invoice i where i.id = :id")
	Invoice findOneInvoiceById(int id);

	@Query("select s from Sponsorship s where s.id = :id")
	Sponsorship findOneSponsorshipById(int id);

	@Query("select s from Sponsorship s where s.published = false AND s.sponsor.id = :id")
	Collection<Sponsorship> findSponsorUnpublishedSponsorship(int id);

	@Query("select s from Sponsorship s where s.sponsor.id = :id")
	Collection<Sponsorship> findSponsorshipBySponsorId(int id);

	@Query("select i from Invoice i where i.code = :code")
	Invoice findInvoiceByCode(String code);

	@Query("select i from Invoice i where i.sponsorship.id = :id")
	Collection<Invoice> findAllInvoicesBySponsorshipId(int id);

	@Query("SELECT config FROM SystemConfiguration config")
	SystemConfiguration findSystemConfiguration();

}
