
package acme.features.sponsor.invoices;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.invoices.Invoice;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Service
public class SponsorInvoiceListService extends AbstractService<Sponsor, Invoice> {

	@Autowired
	protected SponsorInvoiceRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		Sponsorship object;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		object = this.repository.findSponsorshipById(masterId);
		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();
		super.getResponse().setAuthorised(object.getSponsor().getUserAccount().getId() == userAccountId);
	}

	@Override
	public void load() {
		Collection<Invoice> objects;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		objects = this.repository.findInvoicesBySponsorship(masterId);
		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Invoice object) {
		assert object != null;
		Dataset dataset;
		dataset = super.unbind(object, "code", "dueDate", "quantity");
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		dataset.put("masterId", masterId);
		final Sponsorship p = this.repository.findSponsorshipById(masterId);
		final boolean showCreate = p.isDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addData(dataset);
	}

	@Override
	public void unbind(final Collection<Invoice> object) {
		assert object != null;
		int masterId;
		masterId = super.getRequest().getData("masterId", int.class);
		super.getResponse().addGlobal("masterId", masterId);
		final Sponsorship c = this.repository.findSponsorshipById(masterId);
		final boolean showCreate = c.isDraftMode();
		super.getResponse().addGlobal("showCreate", showCreate);
	}

}
