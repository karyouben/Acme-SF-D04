
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
public class ManagerUserStoryShowService extends AbstractService<Manager, UserStory> {
	//Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryRepository repository;

	//AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		UserStory userStory = this.repository.findUserStoryById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = userStory != null && userStory.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		UserStory userStory = this.repository.findUserStoryById(id);

		super.getBuffer().addData(userStory);
	}

	@Override
	public void unbind(final UserStory object) {
		SelectChoices choices = SelectChoices.from(Priority.class, object.getPriority());

		Dataset dataset = super.unbind(object, "title", "description", "cost", "acceptanceCriteria", "link", "priority", "draftMode");

		dataset.put("priorityOptions", choices);

		super.getResponse().addData(dataset);
	}

}
