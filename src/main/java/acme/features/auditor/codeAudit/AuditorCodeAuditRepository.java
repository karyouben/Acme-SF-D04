
package acme.features.auditor.codeAudit;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Repository
public interface AuditorCodeAuditRepository extends AbstractRepository {

	@Query("select c from CodeAudit c where c.auditor.id = :auditorId")
	List<CodeAudit> findAllCodeAuditByAuditorId(int auditorId);

	@Query("select a from Auditor a  where a.id = :auditorId")
	Auditor findAuditorById(int auditorId);

	@Query("select c from CodeAudit c")
	List<CodeAudit> findAllCodeAudits();

	@Query("select c from CodeAudit c where c.id = :id")
	CodeAudit findOneCodeAuditById(int id);

	@Query("select p from Project p where p.id = :id")
	Project findOneProjectById(int id);

	@Query("select a from AuditRecord a where a.codeAudit.id = :codeAuditId")
	Collection<AuditRecord> findAuditRecordsByCodeAuditId(int codeAuditId);

	@Query("select p from Project p")
	Collection<Project> findAllProjects();

	@Query("select a.mark from AuditRecord a where a.codeAudit.id = :auditId")
	Collection<Mark> findMarksByAuditId(int auditId);

	@Query("select c from CodeAudit c where c.code = :code and c.id != :auditId")
	CodeAudit findCodeAuditByCodeDifferentId(String code, int auditId);
}
