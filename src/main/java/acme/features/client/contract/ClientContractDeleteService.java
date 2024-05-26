
package acme.features.client.contract;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.entities.project.Project;
import acme.roles.client.Client;

@Service
public class ClientContractDeleteService extends AbstractService<Client, Contract> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClientContractRepository repository;


	@Override
	public void authorise() {
		boolean status;
		int masterId;

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		masterId = super.getRequest().getData("id", int.class);
		Contract contract = this.repository.findContractById(masterId);
		Client client = contract == null ? null : contract.getClient();
		status = contract != null && contract.isDraftMode() && principal.hasRole(client) && contract.getClient().getUserAccount().getId() == userAccountId;

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

		int masterId = super.getRequest().getData("id", int.class);
		List<Progress> ls = this.repository.findProgresssByContractId(masterId).stream().toList();
		final boolean someDraftProgress = ls.stream().anyMatch(progress -> !progress.isDraftMode());
		super.state(!someDraftProgress, "*", "client.contract.form.error.some-publish");
	}

	@Override
	public void perform(final Contract object) {
		assert object != null;

		Collection<Progress> progress = this.repository.findProgresssByContractId(object.getId());

		this.repository.deleteAll(progress);

		this.repository.delete(object);
	}

}
