
package acme.features.auditor.auditRecord;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorAuditRecordCreateService extends AbstractService<Auditor, AuditRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorAuditRecordRepository repository;

	// AbstractService<Auditor, AuditRecord> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		AuditRecord object;

		object = new AuditRecord();
		object.setDraftMode(true);

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
		Date pastMostDate = MomentHelper.parse("2000/01/01 00:00", "yyyy/MM/dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("codeAudit"))
			super.state(object.getCodeAudit().isDraftMode(), "codeAudit", "validation.auditrecord.published.audit-is-published");

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			AuditRecord isCodeUnique;
			isCodeUnique = this.repository.findAuditRecordByCode(object.getCode());
			super.state(isCodeUnique == null, "code", "validation.auditrecord.code.duplicate");
		}
		if (object.getInitialMoment() != null && !super.getBuffer().getErrors().hasErrors("initialMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getInitialMoment(), pastMostDate), "initialMoment", "validation.auditrecord.moment.minimum-date");
		if (object.getFinalMoment() != null && !super.getBuffer().getErrors().hasErrors("finalMoment"))
			super.state(MomentHelper.isAfterOrEqual(object.getFinalMoment(), pastMostDate), "finalMoment", "validation.auditrecord.moment.minimum-date");
		if (object.getInitialMoment() != null && object.getFinalMoment() != null) {
			if (!super.getBuffer().getErrors().hasErrors("initialMoment"))
				super.state(MomentHelper.isAfterOrEqual(object.getInitialMoment(), pastMostDate), "initialMoment", "validation.auditrecord.moment.minimum-date");

			if (!super.getBuffer().getErrors().hasErrors("finalMoment"))
				super.state(MomentHelper.isAfterOrEqual(object.getFinalMoment(), pastMostDate), "finalMoment", "validation.auditrecord.moment.minimum-date");

			if (!super.getBuffer().getErrors().hasErrors("initialMoment"))
				super.state(MomentHelper.isAfter(object.getFinalMoment(), object.getInitialMoment()), "initialMoment", "validation.auditrecord.moment.initial-after-final");

			if (!super.getBuffer().getErrors().hasErrors("finalMoment")) {
				Date minimumEnd;

				minimumEnd = MomentHelper.deltaFromMoment(object.getInitialMoment(), 1, ChronoUnit.HOURS);
				super.state(MomentHelper.isAfterOrEqual(object.getFinalMoment(), minimumEnd), "finalMoment", "validation.auditrecord.moment.minimum-one-hour");
			}
		}
	}

	@Override
	public void perform(final AuditRecord object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final AuditRecord object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices codeAudits;
		Dataset dataset;

		Collection<CodeAudit> allCodeAudits = this.repository.findAllCodeAudits();
		codeAudits = SelectChoices.from(allCodeAudits, "code", object.getCodeAudit());
		choices = SelectChoices.from(Mark.class, object.getMark());

		dataset = super.unbind(object, "code", "draftMode", "link", "mark", "initialMoment", "finalMoment");
		dataset.put("codAudit", codeAudits.getSelected().getKey());
		dataset.put("codeaudits", codeAudits);
		dataset.put("marks", choices);

		super.getResponse().addData(dataset);
	}

}
