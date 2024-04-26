
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

public interface SponsorInvoiceRepository extends AbstractRepository {

	@Query("select us from Invoice us where us.sponsorship.id = :id")
	Collection<Invoice> findInvoicesBySponsorship(int id);

	@Query("select us from Invoice us where us.sponsorship.sponsor = :sponsor")
	Collection<Invoice> findInvoicesBySponsor(Sponsor sponsor);

	@Query("select us from Invoice us where us.id = :id")
	Invoice findInvoiceById(int id);
	@Query("select us from Invoice us where us.code = :code")
	Invoice findInvoiceByCode(String code);

	@Query("select m from Sponsorship m where m.id = :id")
	Sponsorship findSponsorshipById(int id);

	@Query("select m from Sponsor m where m.id = :id")
	Sponsor findSponsorById(int id);

}
