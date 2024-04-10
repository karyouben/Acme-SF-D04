
package acme.features.manager.assignment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Assignment;
import acme.roles.Manager;

@Controller
public class ManagerAssignmentController extends AbstractController<Manager, Assignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerAssignmentListService		listService;

	@Autowired
	protected ManagerAssignmentShowService		showService;

	@Autowired
	protected ManagerAssignmentCreateService	createService;

	@Autowired
	protected ManagerAssignmentDeleteService	deleteService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addCustomCommand("list-by-proyect", "list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
	}

}
