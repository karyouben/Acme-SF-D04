
package acme.features.auditor.dashboard;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.codeAudits.Type;

@Repository
public interface AuditorDashboardRepository extends AbstractRepository {

	@Query("select c.type from CodeAudit c where c.auditor.id = :auditorId")
	Collection<Type> findCodeAuditsAsTypes(int auditorId);

	default Map<Type, Integer> totalTypes(final int managerId) {
		Map<Type, Integer> result = new EnumMap<>(Type.class);
		Collection<Type> Types = this.findCodeAuditsAsTypes(managerId);

		for (Type type : Type.values())
			result.put(type, 0);

		for (Type type : Types)
			result.put(type, result.get(type) + 1);

		return result;
	}

	@Query("select (select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Collection<Double> auditingRecordsPerAudit(int auditorId);

	@Query("select avg(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Double averageAuditingRecords(int auditorId);

	@Query("select max(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Integer maxAuditingRecords(int auditorId);

	@Query("select min(select count(ar) from AuditRecord ar where ar.codeAudit.id = a.id) from CodeAudit a where a.auditor.id = :auditorId")
	Integer minAuditingRecords(int auditorId);

	@Query("select avg(time_to_sec(timediff(ar.finalMoment, ar.initialMoment)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double averageRecordPeriod(int auditorId);

	@Query("select stddev(time_to_sec(timediff(ar.finalMoment, ar.initialMoment)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double deviationRecordPeriod(int auditorId);

	@Query("select min(time_to_sec(timediff(ar.finalMoment, ar.initialMoment)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double minimumRecordPeriod(int auditorId);

	@Query("select max(time_to_sec(timediff(ar.finalMoment, ar.initialMoment)) / 3600) from AuditRecord ar where ar.codeAudit.auditor.id = :auditorId")
	Double maximumRecordPeriod(int auditorId);

}
