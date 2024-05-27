
package acme.features.auditor.auditRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordDeleteService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService<Auditor, AuditRecord> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		AuditRecord auditRecord;

		id = super.getRequest().getData("id", int.class);
		auditRecord = this.repository.findOneAuditRecordById(id);

		status = auditRecord != null && auditRecord.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditRecord.getCodeAudit().getAuditor());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		AuditRecord object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneAuditRecordById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final AuditRecord object) {
		assert object != null;

		int codeAuditId;
		CodeAudit codeAudit;

		codeAuditId = super.getRequest().getData("codeAudit", int.class);
		codeAudit = this.repository.findOneCodeAuditById(codeAuditId);

		object.setCodeAudit(codeAudit);
		super.bind(object, "code", "link", "mark", "initialMoment", "finalMoment");
	}

	@Override
	public void validate(final AuditRecord object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("draftMode"))
			super.state(object.isDraftMode(), "draftMode", "validation.auditrecord.draftMode");

	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

	}

}
