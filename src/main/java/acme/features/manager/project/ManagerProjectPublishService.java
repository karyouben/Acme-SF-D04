
package acme.features.manager.project;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Assignment;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int masterId = super.getRequest().getData("id", int.class);
		Project project = this.repository.findProjectById(masterId);

		List<UserStory> ls = this.repository.findAssignmentsByProjectId(masterId).stream().map(Assignment::getUserStory).toList();

		boolean status = project != null && project.isDraftMode() && !project.isHasErrors() && principal.hasRole(project.getManager()) && project.getManager().getUserAccount().getId() == userAccountId && !ls.isEmpty()
			&& ls.stream().allMatch(us -> !us.isDraftMode());

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
		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "abstract$", "link", "totalCost", "draftMode");

		super.getResponse().addData(dataset);
	}

}
