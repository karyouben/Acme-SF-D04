
package acme.features.manager.userStory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Priority;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Service
public class ManagerUserStoryCreateService extends AbstractService<Manager, UserStory> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		final boolean authorise = principal.hasRole(Manager.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		UserStory userStory = new UserStory();
		userStory.setDraftMode(true);
		final int id = super.getRequest().getPrincipal().getActiveRoleId();
		userStory.setManager(this.repository.findManagerById(id));

		super.getBuffer().addData(userStory);
	}

	@Override
	public void bind(final UserStory object) {
		assert object != null;

		super.bind(object, "title", "description", "cost", "acceptanceCriteria", "link", "priority");
	}

	@Override
	public void validate(final UserStory object) {
		assert object != null;
	}

	@Override
	public void perform(final UserStory object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final UserStory object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(Priority.class, object.getPriority());

		Dataset dataset = super.unbind(object, "title", "description", "cost", "acceptanceCriteria", "link", "priority", "draftMode");

		dataset.put("priorityOptions", choices);

		super.getResponse().addData(dataset);
	}

}
