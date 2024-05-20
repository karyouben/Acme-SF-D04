
package acme.features.manager.assignment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.services.AbstractService;
import acme.entities.project.Assignment;
import acme.roles.Manager;

@Service
public class ManagerAssignmentDeleteService extends AbstractService<Manager, Assignment> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerAssignmentRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Assignment assignment = this.repository.findAssignmentById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = assignment != null && assignment.getProject().getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Assignment assignment = this.repository.findAssignmentById(id);

		super.getBuffer().addData(assignment);
	}

	@Override
	public void bind(final Assignment object) {
		assert object != null;

		super.bind(object, "userStory");
	}

	@Override
	public void validate(final Assignment object) {
		assert object != null;
	}

	@Override
	public void perform(final Assignment object) {
		assert object != null;

		this.repository.delete(object);
	}

}
