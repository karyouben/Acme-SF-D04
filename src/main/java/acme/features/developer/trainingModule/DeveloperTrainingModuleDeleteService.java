
package acme.features.developer.trainingModule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleDeleteService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {
		boolean status;
		int masterId;

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		masterId = super.getRequest().getData("id", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(masterId);
		Developer developer = trainingModule == null ? null : trainingModule.getDeveloper();
		status = trainingModule != null && trainingModule.isDraftMode() && principal.hasRole(developer) && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		TrainingModule object = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime");
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.delete(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		Dataset dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime");

		dataset.put("difficultyLevelOptions", choices);

		super.getResponse().addData(dataset);
	}

}
