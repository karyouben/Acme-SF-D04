
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectUpdateService extends AbstractService<Manager, Project> {
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
		Manager manager = project == null ? null : project.getManager();
		status = project != null && project.isDraftMode() && principal.hasRole(manager) && project.getManager().getUserAccount().getId() == userAccountId;

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

		super.bind(object, "code", "title", "abstract$", "link", "totalCost");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int proyectId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllProjects().stream().filter(e -> e.getId() != proyectId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("totalCost")) {
			final boolean duplicatedCode = object.getTotalCost().getAmount() < 0;

			super.state(!duplicatedCode, "totalCost", "manager.project.form.error.negative-total-cost");
		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setRemainingCost(object.getTotalCost());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstract$", "link", "totalCost", "draftMode");

		super.getResponse().addData(dataset);
	}

}
