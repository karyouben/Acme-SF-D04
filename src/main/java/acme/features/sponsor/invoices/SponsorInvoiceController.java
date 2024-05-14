
package acme.features.sponsor.invoices;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.invoices.Invoice;
import acme.roles.Sponsor;

@Controller
public class SponsorInvoiceController extends AbstractController<Sponsor, Invoice> {

	@Autowired
	protected SponsorInvoiceListService		listService;

	@Autowired
	protected SponsorInvoiceShowService		showService;

	@Autowired
	protected SponsorInvoiceCreateService	createService;

	@Autowired
	protected SponsorInvoiceUpdateService	updateService;

	@Autowired
	protected SponsorInvoicePublishService	publishService;

	@Autowired
	protected SponsorInvoiceDeleteService	deleteService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);

	}
}
