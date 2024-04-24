
package acme.features.client.contract;

import java.util.Collection;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Service
public class ClientContractListService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ClientContractRepository repository;

	// AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Contract> contracts;

		final int id = super.getRequest().getPrincipal().getAccountId();
		contracts = this.repository.findAllContractsByClientId(id);

		super.getBuffer().addData(contracts);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		final Dataset dataset = super.unbind(object, "code", "project");
		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);

		if (object.isDraftMode()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("draftMode", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("draftMode", "No");

		super.getResponse().addData(dataset);
	}

}
