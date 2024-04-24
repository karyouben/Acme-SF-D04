
package acme.features.client.progressLog;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int contractId = super.getRequest().getData("contractId", int.class);
		Contract contract = this.repository.findContractById(contractId);

		Progress progress = new Progress();
		progress.setContract(contract);
		progress.setDraftMode(true);

		super.getBuffer().addData(progress);
	}

	@Override
	public void bind(final Progress object) {
		assert object != null;

		Date date = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(date.getTime() - 600000);
		super.bind(object, "record", "completeness", "comment", "registration", "responsable");
		object.setRegistration(creationMoment);
	}

	@Override
	public void validate(final Progress object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("record")) {
			final boolean duplicatedCode = this.repository.findAllProgresss().stream().anyMatch(e -> e.getRecord().equals(object.getRecord()));

			super.state(!duplicatedCode, "record", "client.progress.form.error.duplicated-code");
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
		dataset.put("contractId", super.getRequest().getData("contractId", int.class));

		super.getResponse().addData(dataset);
	}

}
