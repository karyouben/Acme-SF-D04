
package acme.features.sponsor.invoices;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.components.AuxiliarService;
import acme.entities.invoices.Invoice;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceUpdateService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	protected SponsorInvoiceRepository	repository;

	@Autowired
	protected AuxiliarService			auxiliarService;

	// AbstractService<Employer, Job> -------------------------------------


	@Override
	public void authorise() {
		Invoice object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsorship().getSponsor().getUserAccount().getId() == userAccountId && object.isDraftMode());
	}

	@Override
	public void load() {
		Invoice object;
		int id;
		id = super.getRequest().getData("id", int.class);
		object = this.repository.findInvoiceById(id);
		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final Invoice object) {
		assert object != null;
		super.bind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link");
	}

	@Override
	public void validate(final Invoice object) {
		assert object != null;
		if (!super.getBuffer().getErrors().hasErrors("quantity"))
			super.state(this.auxiliarService.validatePrice(object.getQuantity(), 0, 1000000), "quantity", "sponsor.invoice.form.error.quantity");
		if (!super.getBuffer().getErrors().hasErrors("tax"))
			super.state(this.auxiliarService.validatePrice(object.getQuantity(), 0, 1000000), "tax", "sponsor.invoice.form.error.tax");
		if (!super.getBuffer().getErrors().hasErrors("code")) {
			Invoice existing;
			existing = this.repository.findInvoiceByCode(object.getCode());
			final Invoice sponsorship2 = object.getCode().equals("") || object.getCode().equals(null) ? null : this.repository.findInvoiceById(object.getId());
			super.state(existing == null || sponsorship2.equals(existing), "code", "sponsor.sponsorship.form.error.code");
		}
		if (!super.getBuffer().getErrors().hasErrors("registrationTime")) {
			super.state(MomentHelper.isBefore(object.getRegistrationTime(), MomentHelper.getCurrentMoment()), "registrationTime", "sponsor.sponsorship.form.error.start-period");
			super.state(this.auxiliarService.validateDate(object.getRegistrationTime()), "registrationTime", "sponsor.sponsorship.form.error.dates");
		}
		if (!super.getBuffer().getErrors().hasErrors("dueDate")) {
			Date minimumEndDate;
			super.state(this.auxiliarService.validateDate(object.getDueDate()), "dueDate", "sponsor.sponsorship.form.error.dates");
			if (object.getRegistrationTime() != null) {
				minimumEndDate = MomentHelper.deltaFromMoment(object.getRegistrationTime(), 30, ChronoUnit.DAYS);
				super.state(MomentHelper.isAfterOrEqual(object.getDueDate(), minimumEndDate), "dueDate", "sponsor.sponsorship.form.error.end-period");

			}
		}
	}

	@Override
	public void perform(final Invoice object) {
		assert object != null;
		this.repository.save(object);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "registrationTime", "dueDate", "quantity", "tax", "link", "draftMode", "sponsorship");
		dataset.put("totalAmount", object.totalAmount());
		super.getResponse().addData(dataset);
	}

}
