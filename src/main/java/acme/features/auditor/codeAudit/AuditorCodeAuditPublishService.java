
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.auditRecords.AuditRecord;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.Type;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditPublishService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuditorCodeAuditRepository repository;

	// AbstractService<Auditor, CodeAudit> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int id;
		Auditor auditor;
		CodeAudit codeAudit;

		id = super.getRequest().getData("id", int.class);
		codeAudit = this.repository.findOneCodeAuditById(id);

		auditor = codeAudit == null ? null : codeAudit.getAuditor();
		status = codeAudit != null && codeAudit.isDraftMode() && super.getRequest().getPrincipal().hasRole(auditor);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		CodeAudit object;
		int id;

		id = super.getRequest().getData("id", int.class);
		object = this.repository.findOneCodeAuditById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		super.bind(object, "publish");
	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		String markMode;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		markMode = MarkMode.calculate(marks);

		if (!super.getBuffer().getErrors().hasErrors("markMode"))
			if (markMode != null) {
				boolean isMarkAtLeastC = markMode.equals("C") || markMode.equals("B") || markMode.equals("A") || markMode.equals("A_PLUS");
				super.state(isMarkAtLeastC, "markMode", "validation.codeaudit.mode.less-than-c");
			} else
				super.state(false, "markMode", "validation.codeaudit.mode.less-than-c");

		Collection<AuditRecord> auditRecords;

		auditRecords = this.repository.findAuditRecordsByCodeAuditId(object.getId());

		super.state(auditRecords.stream().noneMatch(AuditRecord::isDraftMode), "*", "validation.codeaudit.publish.unpublished-audit-records");
	}

	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		object.setDraftMode(false);
		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projects;
		Dataset dataset;
		String markMode;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		markMode = MarkMode.calculate(marks);

		Collection<Project> allProjects = this.repository.findAllProjects();
		projects = SelectChoices.from(allProjects, "title", object.getProject());
		choices = SelectChoices.from(Type.class, object.getType());

		dataset = super.unbind(object, "code", "draftMode", "execution", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);
		dataset.put("markMode", markMode);

		super.getResponse().addData(dataset);
	}
}
