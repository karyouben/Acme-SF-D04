
package acme.features.manager.assignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Assignment;
import acme.entities.project.Project;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerAssignmentCreateService extends AbstractService<Manager, Assignment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAssignmentRepository repository;

	// AbstractService<Authenticated, Assignment> ---------------------------


	@Override
	public void authorise() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repository.findProjectById(projectId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = project != null && principal.hasRole(Manager.class) && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		final int projectId = super.getRequest().getData("projectId", int.class);
		Project project = this.repository.findProjectById(projectId);

		Assignment object = new Assignment();
		object.setProject(project);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Assignment object) {
		assert object != null;

		super.bind(object, "userStory");
	}

	@Override
	public void validate(final Assignment object) {
		assert object != null;
		final int projectId = super.getRequest().getData("projectId", int.class);

		if (!super.getBuffer().getErrors().hasErrors("userStory")) {
			final boolean duplicatedUS = this.repository.findAssignmentsByProjectId(projectId).stream().anyMatch(a -> a.getUserStory().equals(object.getUserStory()));

			super.state(!duplicatedUS, "userStory", "manager.project.form.error.duplicated-userStory");

			Project project = this.repository.findProjectById(projectId);
			int addedCost = this.repository.findAssignmentsByProjectId(projectId).stream().mapToInt(a -> a.getUserStory().getCost()).sum();

			final boolean costExceeded = project.getTotalCost().getAmount() < addedCost + object.getUserStory().getCost();

			super.state(!costExceeded, "userStory", "manager.project.form.error.cost-exceeded");
		}

	}

	@Override
	public void perform(final Assignment object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Assignment object) {
		assert object != null;
		final int projectId = super.getRequest().getData("projectId", int.class);

		int managerID = object.getProject().getManager().getUserAccount().getId();
		Collection<UserStory> userStories = this.repository.findUserStoriesByManagerId(managerID);
		SelectChoices choices = SelectChoices.from(userStories, "title", object.getUserStory());

		Dataset dataset = super.unbind(object, "userStory");

		dataset.put("userStories", choices);
		dataset.put("projectId", projectId);

		super.getResponse().addData(dataset);
	}

}
