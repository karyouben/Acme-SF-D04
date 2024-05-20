
package acme.features.manager.project;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.datatypes.Money;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.entities.systemconf.SystemConfiguration;
import acme.roles.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository repository;

	// AbstractService<Authenticated, Project> ---------------------------


	@Override
	public void authorise() {
		boolean status;

		status = super.getRequest().getPrincipal().hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		Project object = new Project();
		object.setManager(this.repository.findManagerById(id));
		object.setDraftMode(true);

		object.setRemainingCost(new Money());

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Project object) {
		assert object != null;

		super.bind(object, "code", "title", "projectAbstract", "link", "totalCost", "hasErrors");
	}

	@Override
	public void validate(final Project object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllProjects().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "manager.project.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("totalCost")) {
			final boolean negative = object.getTotalCost().getAmount() < 0;

			super.state(!negative, "totalCost", "manager.project.form.error.negative-total-cost");

			final boolean tooBig = object.getTotalCost().getAmount() > 9999999999.99;

			super.state(!tooBig, "totalCost", "manager.project.form.error.exceed-limit-total-cost");

			List<SystemConfiguration> sc = this.repository.findSystemConfiguration();
			final boolean foundCurrency = Stream.of(sc.get(0).acceptedCurrencies.split(",")).anyMatch(c -> c.equals(object.getTotalCost().getCurrency()));

			super.state(foundCurrency, "totalCost", "manager.project.form.error.currency-not-supported");

		}

	}

	@Override
	public void perform(final Project object) {
		assert object != null;

		object.setRemainingCost(object.getTotalCost());

		this.repository.save(object);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "totalCost", "hasErrors");

		super.getResponse().addData(dataset);
	}

}
