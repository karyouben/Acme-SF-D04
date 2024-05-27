
package acme.features.administrator.administratordashboard;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.data.accounts.Administrator;
import acme.client.repositories.AbstractRepository;

@Repository
public interface AdministratorDashboardRepository extends AbstractRepository {

	@Query("SELECT d FROM Administrator d WHERE d.userAccount.id = :id")
	Administrator findAdministratorById(int id);

	@Query("SELECT COUNT(a) FROM Administrator a")
	int totalAdministrators();

	@Query("SELECT COUNT(a) FROM Auditor a")
	int totalAuditors();

	@Query("SELECT COUNT(c) FROM Consumer c")
	int totalConsumers();

	@Query("SELECT COUNT(d) FROM Developer d")
	int totalDevelopers();

	@Query("SELECT COUNT(m) FROM Manager m")
	int totalManagers();

	@Query("SELECT COUNT(p) FROM Provider p")
	int totalProviders();

	@Query("SELECT COUNT(s) FROM Sponsor s")
	int totalSponsors();

	@Query("SELECT COUNT(c) FROM Client c")
	int totalClients();

	@Query("SELECT COUNT(n) FROM Notice n WHERE n.email IS NOT NULL AND n.link IS NOT NULL")
	int totalNoticeWithEmailAndLink();

	@Query("SELECT COUNT(n) FROM Notice n")
	int totalNotice();

	@Query("SELECT COUNT(o) FROM Objective o WHERE o.isCritical = TRUE")
	int totalCriticalObjectives();

	@Query("SELECT COUNT(o) FROM Objective o WHERE o.isCritical = FALSE")
	int totalNonCriticalObjectives();

	@Query("SELECT COUNT(o) FROM Objective o")
	int totalObjectives();

	@Query("select avg(r.impact * r.probability) FROM Risk r")
	Double findAverageRiskValue();

	@Query("select stddev(r.impact * r.probability) FROM Risk r")
	Double findDeviationRiskValue();

	@Query("select max(r.impact * r.probability) FROM Risk r")
	Double findMaximumRiskValue();

	@Query("select min(r.impact * r.probability) FROM Risk r")
	Double findMinimumRiskValue();

	@Query("select day(c.instantiationMoment), count(c) from Claim c where c.instantiationMoment > :thresholdDate group by day(c.instantiationMoment)")
	List<Object[]> numberOfClaimsByDay(Date thresholdDate);

	@Query("select count(c) from Claim c where c.instantiationMoment >= :startDate and c.instantiationMoment < :endDate")
	Long numberOfClaimsBetweenDates(Date startDate, Date endDate);

}
