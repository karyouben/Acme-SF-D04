
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Service
public class ClientContractPublishService extends AbstractService<Client, Contract> {

	@Autowired
	protected ClientContractRepository repository;

	// AbstractService<Authenticated, Contract> ---------------------------


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		int masterId = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findContractById(masterId);

		List<Progress> ls = this.repository.findProgresssByContractId(masterId).stream().toList();

		boolean status = contract != null && contract.isDraftMode() && principal.hasRole(contract.getClient()) && contract.getClient().getUserAccount().getId() == userAccountId && !ls.isEmpty() && ls.stream().anyMatch(us -> !us.isDraftMode());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);

		Contract object = this.repository.findContractById(id);

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

		final String CREATION_MOMENT = "creationMoment";
		final String UPDATE_MOMENT = "updateMoment";

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllContracts().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "client.contract.form.error.duplicated-code");
		}

		if (!super.getBuffer().getErrors().hasErrors("budget")) {
			final boolean duplicatedCode = object.getBudget() < object.getProject().getTotalCost().getAmount();

			super.state(!duplicatedCode, "budget", "client.contract.form.error.budget-total-cost");
		}

	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		object.setDraftMode(false);

		this.repository.save(object);
	}

	@Override
	public void unbind(final Contract object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		Dataset dataset = super.unbind(object, "code", "instantiation", "providerName", "customerName", "goals", "budget", "project", "draftMode");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);
	}

}
