
package acme.features.sponsor.dashboard;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.roles.Sponsor;

@Repository
public interface SponsorDashboardRepository extends AbstractRepository {

	@Query("select count(us) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false and us.tax <= 21 ")
	Optional<Integer> findNumOfInvoicesWithTax21less(Sponsor sponsor);

	@Query("select count(us) from Sponsorship us where us.sponsor = :sponsor and us.draftMode=false and us.link is not null  ")
	Optional<Integer> findNumOfSponsorshipsWithLink(Sponsor sponsor);

	@Query("select avg(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false")
	Optional<Double> findAverageSponsorshipCost(Sponsor sponsor);

	@Query("select max(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false")
	Optional<Double> findMaxSponsorshipCost(Sponsor sponsor);

	@Query("select min(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false")
	Optional<Double> findMinSponsorshipCost(Sponsor sponsor);

	@Query("select stddev(p.amount.amount) from Sponsorship p where p.sponsor = :sponsor and p.draftMode=false")
	Optional<Double> findLinearDevSponsorshipCost(Sponsor sponsor);

	@Query("select m from Sponsor m where m.userAccount.id = :id")
	Sponsor findOneSponsorByUserAccountId(int id);

	@Query("select ua from UserAccount ua where ua.id = :id")
	UserAccount findOneUserAccountById(int id);

	@Query("select avg(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false")
	Optional<Double> findAverageInvoiceQuantity(Sponsor sponsor);

	@Query("select max(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false")
	Optional<Double> findMaxInvoiceQuantity(Sponsor sponsor);

	@Query("select min(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false")
	Optional<Double> findMinInvoiceQuantity(Sponsor sponsor);

	@Query("select stddev(us.quantity.amount) from Invoice us where us.sponsorship.sponsor = :sponsor and us.draftMode=false")
	Optional<Double> findLinearDevInvoiceQuantity(Sponsor sponsor);

}
