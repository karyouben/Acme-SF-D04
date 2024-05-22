
package acme.features.developer.trainingSession;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.trainingModule.TrainingModule;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Service
public class DeveloperTrainingSessionCreateService extends AbstractService<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {
		int developerId;
		int trainingModuleId;
		TrainingModule trainingModule;
		Boolean status;

		developerId = super.getRequest().getPrincipal().getActiveRoleId();
		trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		status = developerId == trainingModule.getDeveloper().getId();
		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {

		final int trainingModuleId = super.getRequest().getData("trainingModuleId", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(trainingModuleId);

		TrainingSession trainingSession = new TrainingSession();
		trainingSession.setTrainingModule(trainingModule);
		trainingSession.setDraftMode(true);

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

				super.state(createOneWeekBeforeEndMinimum, PERIOD_START, "developer.trainingSession.form.error.end-before-creation");
			}
		}

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllTrainingSessions().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingSession.form.error.duplicated-code");
		}

		int masterId = super.getRequest().getData("trainingModuleId", int.class);
		TrainingModule trainingModule = this.repository.findTrainingModuleById(masterId);
		final boolean noDraftTrainingModule = trainingModule.isDraftMode();
		super.state(noDraftTrainingModule, "*", "developer.trainingSession.form.error.trainingModule-noDraft");

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
			super.state(MomentHelper.isAfterOrEqual(object.getEndPeriod(), MomentHelper.deltaFromMoment(minDate, 7, ChronoUnit.DAYS)), PERIOD_END, "developer.training-session.form.error.no-time-for-min-period-duration");
	}

	@Override
	public void perform(final TrainingSession object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingSession object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "startPeriod", "endPeriod", "location", "instructor", "contactEmail", "link", "draftMode");
		dataset.put("trainingModuleId", super.getRequest().getData("trainingModuleId", int.class));

		super.getResponse().addData(dataset);
	}

}
