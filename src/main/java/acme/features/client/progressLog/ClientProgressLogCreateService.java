
package acme.features.client.progressLog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.contract.Contract;
import acme.entities.contract.Progress;
import acme.roles.client.Client;

@Service
public class ClientProgressLogCreateService extends AbstractService<Client, Progress> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ClientProgressLogRepository repository;


	@Override
	public void authorise() {
		final Principal principal = super.getRequest().getPrincipal();

		final boolean authorise = principal.hasRole(Client.class);

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		Contract contract = this.repository.findContractById(409);
		Progress progress = new Progress();
		progress.setContract(contract);
		progress.setDraftMode(true);
		super.getBuffer().addData(progress);
	}

	@Override
	public void bind(final Progress object) {
		assert object != null;

		super.bind(object, "record", "completeness", "comment", "registration", "responsable");
	}

	@Override
	public void validate(final Progress object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("record")) {
			final boolean duplicatedCode = this.repository.findAllProgresss().stream().anyMatch(e -> e.getRecord().equals(object.getRecord()));

			super.state(!duplicatedCode, "record", "client.progress.form.error.duplicated-record");
		}
	}

	@Override
	public void perform(final Progress object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final Progress object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "record", "completeness", "comment", "registration", "responsable", "draftMode");

		super.getResponse().addData(dataset);
	}

}
