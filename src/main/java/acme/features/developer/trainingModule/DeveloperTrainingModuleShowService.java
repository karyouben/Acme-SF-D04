
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
public class DeveloperTrainingModuleShowService extends AbstractService<Developer, TrainingModule> {

	//Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingModuleRepository repository;

	//AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = trainingModule != null && principal.hasRole(Developer.class) && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(id);

		super.getBuffer().addData(trainingModule);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		Dataset dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "draftMode");

		dataset.put("difficultyLevelOptions", choices);

		super.getResponse().addData(dataset);
	}

}
