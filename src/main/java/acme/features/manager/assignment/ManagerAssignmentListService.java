
package acme.features.manager.assignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Assignment;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerAssignmentListService extends AbstractService<Manager, Assignment> {
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
		Collection<Assignment> assignments = this.repository.findAssignmentsByProjectId(projectId);

		super.getBuffer().addData(assignments);
	}

	@Override
	public void unbind(final Assignment object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "userStory");

		super.getResponse().addData(dataset);
	}

}
