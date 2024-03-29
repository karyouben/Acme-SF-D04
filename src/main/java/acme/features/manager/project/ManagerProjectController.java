
package acme.features.manager.project;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.Project;
import acme.roles.Manager;

@Controller
public class ManagerProjectController extends AbstractController<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectListService		listService;

	@Autowired
	protected ManagerProjectShowService		showService;

	@Autowired
	protected ManagerProjectCreateService	createService;

	@Autowired
	protected ManagerProjectUpdateService	updateService;

	@Autowired
	protected ManagerProjectDeleteService	deleteService;

	@Autowired
	protected ManagerProjectPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
