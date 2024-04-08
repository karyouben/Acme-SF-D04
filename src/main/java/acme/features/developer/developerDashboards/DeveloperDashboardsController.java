
package acme.features.developer.developerDashboards;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.DeveloperDashboards;
import acme.roles.Developer;

@Controller
public class DeveloperDashboardsController extends AbstractController<Developer, DeveloperDashboards> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected DeveloperDashboardsShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}
}
