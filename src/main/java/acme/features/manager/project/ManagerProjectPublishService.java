
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
		boolean status = project != null && project.getManager().getUserAccount().getId() == userAccountId && project.isDraftMode();

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

		//super.bind(object, /* "code", "title", "projectAbstract", "link", "totalCost", */ "hasErrors");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		/*
		 * if (!super.getBuffer().getErrors().hasErrors("code")) {
		 * final int proyectId = super.getRequest().getData("id", int.class);
		 * final boolean duplicatedCode = this.repository.findAllProjects().stream().filter(e -> e.getId() != proyectId).anyMatch(e -> e.getCode().equals(object.getCode()));
		 * 
		 * super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated-code");
		 * }
		 * 
		 * if (!super.getBuffer().getErrors().hasErrors("totalCost")) {
		 * final boolean negative = object.getTotalCost().getAmount() < 0;
		 * 
		 * super.state(!negative, "totalCost", "manager.project.form.error.negative-total-cost");
		 * 
		 * final boolean tooBig = object.getTotalCost().getAmount() > 9999999999.99;
		 * 
		 * super.state(!tooBig, "totalCost", "manager.project.form.error.exceed-limit-total-cost");
		 * 
		 * List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
		 * final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getTotalCost().getCurrency()));
		 * 
		 * super.state(foundCurrency, "totalCost", "manager.project.form.error.currency-not-supported");
		 * 
		 * }
		 */

		//if (!super.getBuffer().getErrors().hasErrors("hasErrors")) {
		final boolean hasErrors = object.isHasErrors();

		super.state(!hasErrors, "hasErrors", "manager.project.form.error.hasErrors");
		//}

		int masterId = super.getRequest().getData("id", int.class);
		List<UserStory> ls = this.repository.findAssignmentsByProjectId(masterId).stream().map(Assignment::getUserStory).toList();
		final boolean someDraftUserStory = ls.stream().anyMatch(us -> us.isDraftMode());
		final boolean noUS = ls.isEmpty();
		super.state(!noUS, "*", "manager.project.form.error.userStories-empty");
		super.state(!someDraftUserStory, "*", "manager.project.form.error.userStories-draft");

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		//object.setRemainingCost(object.getTotalCost());
		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "totalCost", "draftMode", "hasErrors");

		super.getResponse().addData(dataset);
	}

}
