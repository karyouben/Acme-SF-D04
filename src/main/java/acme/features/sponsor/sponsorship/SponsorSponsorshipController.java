
package acme.features.sponsor.sponsorship;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.sponsorships.Sponsorship;
import acme.roles.Sponsor;

@Controller
public class SponsorSponsorshipController extends AbstractController<Sponsor, Sponsorship> {

	@Autowired
	protected SponsorSponsorshipListService		listAllService;

	@Autowired
	protected SponsorSponsorshipShowService		showService;

	@Autowired
	protected SponsorSponsorshipCreateService	createService;

	@Autowired
	protected SponsorSponsorshipDeleteService	deleteService;

	@Autowired
	protected SponsorSponsorshipUpdateService	updateService;

	@Autowired
	protected SponsorSponsorshipPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listAllService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("publish", "update", this.publishService);
	}

}
