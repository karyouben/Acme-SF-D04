
package acme.features.client.contract;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Service
public class ClientContractCreateService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		Contract object = new Contract();
		object.setClient(this.repository.findClientById(id));
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Contract object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findProjectById(projectId);

		super.bind(object, "code", "instantiation", "providerName", "customerName", "goals", "budget", "project");
		object.setProject(project);
	}

	@Override
	public void validate(final Contract object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllContracts().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "client.contract.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			final boolean budget = object.getBudget() > object.getProject().getTotalCost().getAmount();

			super.state(!budget, "budget", "client.contract.form.error.budget-total-cost");
		}

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset = super.unbind(object, "code", "instantiation", "providerName", "customerName", "goals", "budget", "project", "draftMode");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);
	}

}
