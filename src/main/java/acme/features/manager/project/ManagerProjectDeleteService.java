
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectDeleteService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		masterId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findProjectById(masterId);
		status = project != null && project.getManager().getUserAccount().getId() == userAccountId && project.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		Project object = this.repository.findProjectById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "link", "totalCost", "hasErrors");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		int id = object.getId();

		// In case the other students implement wrong their part
		super.state(this.repository.codeAuditExist(id) == 0, "*", "manager.project.form.error.has-code-audit");
		super.state(this.repository.contractExist(id) == 0, "*", "manager.project.form.error.has-contract");
		super.state(this.repository.sponsorshipExist(id) == 0, "*", "manager.project.form.error.has-sponsorship");// este
		super.state(this.repository.trainingModuleExist(id) == 0, "*", "manager.project.form.error.has-training-module");
		super.state(this.repository.riskExist(id) == 0, "*", "manager.project.form.error.has-risk"); // este
		super.state(this.repository.objectiveExist(id) == 0, "*", "manager.project.form.error.has-objective");
	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		this.repository.delete(object);
	}

}
