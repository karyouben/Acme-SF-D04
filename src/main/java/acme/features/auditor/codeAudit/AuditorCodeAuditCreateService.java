
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.codeAudits.CodeAudit;
import acme.entities.codeAudits.Type;
import acme.entities.project.Project;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditCreateService extends AbstractService<Auditor, CodeAudit> {

	// Internal state ---------------------------------------------------------

	@Autowired
	AuditorCodeAuditRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		CodeAudit object = new CodeAudit();
		object.setAuditor(this.repository.findAuditorById(id));
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final CodeAudit object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findOneProjectById(projectId);
		object.setProject(project);
		super.bind(object, "code", "execution", "type", "correctiveActions", "link");

	}

	@Override
	public void validate(final CodeAudit object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllCodeAudits().stream().anyMatch(e -> e.getCode().equals(object.getCode()));
			super.state(!duplicatedCode, "code", "validation.codeaudit.code.duplicate");
		}

	}
	@Override
	public void perform(final CodeAudit object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		SelectChoices choices;
		SelectChoices projects;
		Dataset dataset;

		Collection<Project> allProjects = this.repository.findAllProjects();
		projects = SelectChoices.from(allProjects, "code", object.getProject());
		choices = SelectChoices.from(Type.class, object.getType());

		dataset = super.unbind(object, "code", "draftMode", "execution", "type", "correctiveActions", "link");
		dataset.put("project", projects.getSelected().getKey());
		dataset.put("projects", projects);
		dataset.put("auditTypes", choices);

		super.getResponse().addData(dataset);
	}

}
