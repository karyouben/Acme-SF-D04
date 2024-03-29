
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		Project object = new Project();
		object.setManager(this.repository.findManagerById(id));
		object.setDraftMode(true);
		object.setHasErrors(false);

		object.setRemainingCost(new Money());

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
			final boolean duplicatedCode = this.repository.findAllProjects().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

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

		Dataset dataset = super.unbind(object, "code", "title", "abstract$", "link", "totalCost");

		super.getResponse().addData(dataset);
	}

	//	@Override
	//	public void onSuccess() {
	//		if (super.getRequest().getMethod().equals("POST"))
	//			PrincipalHelper.handleUpdate();
	//	}
	//
}
