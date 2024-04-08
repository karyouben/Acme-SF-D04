
package acme.features.developer.trainingSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionPublishService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int id = super.getRequest().getData("id", int.class);
		TrainingSession trainingSession = this.repository.findTrainingSessionById(id);

		final boolean authorise = trainingSession != null && trainingSession.isDraftMode() && principal.hasRole(Developer.class) && trainingSession.getDeveloper().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		TrainingSession trainingSession = this.repository.findTrainingSessionById(id);

		super.getBuffer().addData(trainingSession);
	}

	@Override
	public void bind(final TrainingSession object) {
		assert object != null;

		super.bind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link");
	}

	@Override
	public void validate(final TrainingSession object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int trainingSessionId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllTrainingSessions().stream().filter(e -> e.getId() != trainingSessionId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingSession.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link", "draftMode");

		super.getResponse().addData(dataset);
	}

}
