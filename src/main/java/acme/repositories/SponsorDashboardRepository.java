//
//package acme.repositories;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import acme.entities.sponsorships.Invoice;
//
//public interface SponsorDashboardRepository extends JpaRepository<Invoice, Long> {
//
//	@Query("SELECT COUNT(i) FROM Invoice i WHERE i.tax <= 21")
//	int countInvoicesWithTax21OrLess();
//
//	@Query("SELECT COUNT(s) FROM Sponsorship s WHERE s.link IS NOT NULL")
//	int countSponsorshipsWithLink();
//
//	@Query("SELECT AVG(s.amount), STDDEV(s.amount), MIN(s.amount), MAX(s.amount) FROM Sponsorship s")
//	Object[] sponsorshipAmountStatistics();
//
//	@Query("SELECT AVG(i.quantity), STDDEV(i.quantity), MIN(i.quantity), MAX(i.quantity) FROM Invoice i")
//	Object[] invoiceQuantityStatistics();
//}
