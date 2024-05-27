
package acme.features.administrator.objective;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.objective.Objective;
import acme.entities.objective.Priority;
import acme.entities.project.Project;

@Service
public class AdministratorObjectiveCreateService extends AbstractService<Administrator, Objective> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorObjectiveRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Administrator.class);

		super.getResponse().setAuthorised(status);

	}

	@Override
	public void load() {
		Objective object;
		Date currentMoment = MomentHelper.getCurrentMoment();
		Date instantiation = new Date(currentMoment.getTime() - 1000); //Substracts one second to ensure the moment is in the past
		object = new Objective();
		object.setInstantiation(instantiation);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findProjectById(projectId);

		super.bind(object, "title", "description", "priority", "isCritical", "startDurationPeriod", "endDurationPeriod", "link", "project");

		object.setProject(project);
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			boolean confirmation;
			confirmation = super.getRequest().getData("confirmation", boolean.class);
			super.state(confirmation, "confirmation", "administrator.objective.form.error.confirmation");
		}

		final String PERIOD_START = "startDurationPeriod";
		final String PERIOD_END = "endDurationPeriod";

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START) && !super.getBuffer().getErrors().hasErrors(PERIOD_END)) {
			final boolean startBeforeEnd = MomentHelper.isAfter(object.getEndDurationPeriod(), object.getStartDurationPeriod());
			super.state(startBeforeEnd, PERIOD_END, "administrator.objetive.form.error.end-before-start");

			if (startBeforeEnd) {
				final boolean startOneWeekBeforeEndMinimum = MomentHelper.isLongEnough(object.getStartDurationPeriod(), object.getEndDurationPeriod(), 7, ChronoUnit.DAYS);

				super.state(startOneWeekBeforeEndMinimum, PERIOD_END, "administrator.objetive.form.error.small-display-period");
			}

			if (!super.getBuffer().getErrors().hasErrors("instantiation")) {
				final boolean startAfterInstantiation = MomentHelper.isAfter(object.getStartDurationPeriod(), object.getInstantiation());
				super.state(startAfterInstantiation, PERIOD_START, "administrator.objetive.form.error.start-before-instantiation");
			}
		}

		Date minDate;
		Date maxDate;

		minDate = MomentHelper.parse("2022-07-30 00:00", "yyyy-MM-dd HH:mm");
		maxDate = MomentHelper.parse("2200-12-31 23:59", "yyyy-MM-dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isAfterOrEqual(object.getStartDurationPeriod(), minDate), PERIOD_START, "administrator.objetive.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDurationPeriod(), maxDate), PERIOD_START, "administrator.objetive.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_START))
			super.state(MomentHelper.isBeforeOrEqual(object.getStartDurationPeriod(), MomentHelper.deltaFromMoment(maxDate, -7, ChronoUnit.DAYS)), PERIOD_START, "administrator.objetive.form.error.no-room-for-min-period-duration");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDurationPeriod(), minDate), PERIOD_END, "administrator.objetive.form.error.before-min-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isBeforeOrEqual(object.getEndDurationPeriod(), maxDate), PERIOD_END, "administrator.objetive.form.error.after-max-date");

		if (!super.getBuffer().getErrors().hasErrors(PERIOD_END))
			super.state(MomentHelper.isAfterOrEqual(object.getEndDurationPeriod(), MomentHelper.deltaFromMoment(minDate, 7, ChronoUnit.DAYS)), PERIOD_END, "administrator.objetive.form.error.no-room-for-min-period-duration");
	}

	@Override
	public void perform(final Objective object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Objective object) {
		assert object != null;

		Dataset dataset;
		SelectChoices choices;
		choices = SelectChoices.from(Priority.class, object.getPriority());
		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices;

		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "title", "description", "priority", "isCritical", "startDurationPeriod", "endDurationPeriod", "link", "instantiation", "project");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("confirmation", false);
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
