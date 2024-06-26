
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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

		final boolean authorise = trainingSession != null && trainingSession.isDraftMode() && trainingSession.getTrainingModule().getDeveloper().getUserAccount().getId() == userAccountId;

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

		final String PERIOD_START = "startPeriod";
		final String PERIOD_END = "endPeriod";
		final String CREATION_MOMENT = "creationMoment";

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START) && !super.getBuffer().getErrors().hasErrors(PERIOD_END)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getEndPeriod(), object.getStartPeriod());
			super.state(startBeforeEnd, PERIOD_END, "developer.trainingSession.form.error.end-before-start");

			if (startBeforeEnd) {
				final boolean startOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getStartPeriod(), object.getEndPeriod(), 7, ChronoUnit.DAYS);

				super.state(startOneWeekBeforeEndMinimum, PERIOD_END, "developer.trainingSession.form.error.small-display-period");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors(CREATION_MOMENT) && !super.getBuffer().getErrors().hasErrors(PERIOD_START)) {
			final boolean startBeforeCreation = MomentHelper.isAfter(object.getStartPeriod(), object.getTrainingModule().getCreationMoment());
			super.state(startBeforeCreation, PERIOD_START, "developer.trainingSession.form.error.start-before-creation");

			if (startBeforeCreation) {
				final boolean createOneWeekBeforeStartMinimum = MomentHelper.isLongEnough(object.getTrainingModule().getCreationMoment(), object.getStartPeriod(), 7, ChronoUnit.DAYS);

				super.state(createOneWeekBeforeStartMinimum, PERIOD_START, "developer.trainingSession.form.error.start-before-creation");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors(CREATION_MOMENT) && !super.getBuffer().getErrors().hasErrors(PERIOD_END)) {
			final boolean endBeforeCreation = MomentHelper.isAfter(object.getEndPeriod(), object.getTrainingModule().getCreationMoment());
			super.state(endBeforeCreation, PERIOD_END, "developer.trainingSession.form.error.end-before-creation");

			if (endBeforeCreation) {
				final boolean createOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getTrainingModule().getCreationMoment(), object.getEndPeriod(), 7, ChronoUnit.DAYS);

				super.state(createOneWeekBeforeEndMinimum, PERIOD_END, "developer.trainingSession.form.error.end-before-creation");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final int trainingSessionId = super.getRequest().getData("id", int.class);
			final boolean duplicatedCode = this.repository.findAllTrainingSessions().stream().filter(e -> e.getId() != trainingSessionId).anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingSession.form.error.duplicated-code");
		}

		Date minDate;
		Date maxDate;

		minDate = MomentHelper.parse("2000-01-01 00:00", "yyyy-MM-dd HH:mm");
		maxDate = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isAfterOrEqual(object.getStartPeriod(), minDate), PERIOD_START, "developer.training-session.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartPeriod(), maxDate), PERIOD_START, "developer.training-session.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartPeriod(), MomentHelper.deltaFromMoment(maxDate, -7, ChronoUnit.DAYS)), PERIOD_START, "developer.training-session.form.error.no-room-for-min-period-duration");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), minDate), PERIOD_END, "developer.training-session.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isBeforeOrEqual(object.getEndPeriod(), maxDate), PERIOD_END, "developer.training-session.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), MomentHelper.deltaFromMoment(minDate, 7, ChronoUnit.DAYS)), PERIOD_END, "developer.training-session.form.error.no-room-for-min-period-duration");
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
