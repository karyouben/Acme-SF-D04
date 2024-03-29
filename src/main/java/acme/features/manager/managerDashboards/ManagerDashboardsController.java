
package acme.features.manager.managerDashboards;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ManagerDashboards;
import acme.roles.Manager;

@Controller
public class ManagerDashboardsController extends AbstractController<Manager, ManagerDashboards> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ManagerDashboardsShowService showService;


	// Constructors -----------------------------------------------------------
	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
