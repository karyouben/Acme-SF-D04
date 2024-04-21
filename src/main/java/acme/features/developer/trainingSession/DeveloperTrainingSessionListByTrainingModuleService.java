
package acme.features.developer.trainingSession;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionListByTrainingModuleService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		final int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = trainingModule != null && principal.hasRole(Developer.class) && trainingModule.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		Collection<TrainingSession> trainingSession;

		final int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		trainingSession = this.repository.findTrainingSessionsByTrainingModuleId(trainingModuleId).stream().toList();

		super.getBuffer().addData(trainingSession);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Collection<TrainingModule> trainingModules = this.repository.findAllTrainingModules();
		SelectChoices trainingModulesChoices = SelectChoices.from(trainingModules, "code", object.getTrainingModule());

		final Dataset dataset = super.unbind(object, "code", "trainingModule");
		dataset.put("trainingModule", trainingModulesChoices.getSelected().getLabel());
		dataset.put("trainingModules", trainingModulesChoices);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		int trainingModuleId;

		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);

		super.getResponse().addGlobal("trainingModuleId", trainingModuleId);

		super.getResponse().addData(dataset);
	}

}
