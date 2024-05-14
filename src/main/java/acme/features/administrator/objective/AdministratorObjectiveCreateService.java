
package acme.features.administrator.objective;

import java.time.temporal.ChronoUnit;
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
		Date instantiationMoment;
		instantiationMoment = MomentHelper.getCurrentMoment();
		object = new Objective();
		object.setInstantiation(instantiationMoment);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Objective object) {
		assert object != null;

		super.bind(object, "title", "description", "priority", "isCritical", "startDurationPeriod", "endDurationPeriod", "link");
	}

	@Override
	public void validate(final Objective object) {
		assert object != null;

		String dateString = "2201/01/01 00:00";
		Date futureMostDate = MomentHelper.parse(dateString, "yyyy/MM/dd HH:mm");

		if (!super.getBuffer().getErrors().hasErrors("confirmation")) {
			boolean confirmation;
			confirmation = super.getRequest().getData("confirmation", boolean.class);
			super.state(confirmation, "confirmation", "administrator.objective.form.error.confirmation");
		}
		Date instantiation = MomentHelper.getCurrentMoment();
		if (!super.getBuffer().getErrors().hasErrors("startDurationPeriod"))
			super.state(MomentHelper.isAfter(object.getStartDurationPeriod(), instantiation), "startDurationPeriod", "validation.objective.moment.startDate");

		if (!super.getBuffer().getErrors().hasErrors("endDurationPeriod"))
			super.state(MomentHelper.isAfter(object.getEndDurationPeriod(), instantiation), "endDurationPeriod", "validation.objective.moment.endDate");

		if (!super.getBuffer().getErrors().hasErrors("endDurationPeriod")) {
			Date minimumEnd;
			minimumEnd = MomentHelper.deltaFromMoment(object.getStartDurationPeriod(), 1, ChronoUnit.HOURS);
			super.state(MomentHelper.isAfterOrEqual(object.getEndDurationPeriod(), minimumEnd), "endDurationPeriod", "validation.objective.moment.minimum-one-hour");
		}

		if (!super.getBuffer().getErrors().hasErrors("endDate"))
			super.state(MomentHelper.isBefore(object.getEndDurationPeriod(), futureMostDate), "endDurationPeriod", "admininstrator.objective.form.error.dateOutOfBounds");
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

		dataset = super.unbind(object, "title", "description", "priority", "isCritical", "startDurationPeriod", "endDurationPeriod", "link");
		dataset.put("confirmation", false);
		dataset.put("priorities", choices);

		super.getResponse().addData(dataset);
	}

}
