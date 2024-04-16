
package acme.features.client.clientDashboards;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ClientDashboard;
import acme.roles.client.Client;

@Controller
public class ClientDashboardsController extends AbstractController<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ClientDashboardsShowService showService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("show", this.showService);
	}

}
