
package acme.features.manager.userStory;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.project.UserStory;
import acme.roles.Manager;

@Controller
public class ManagerUserStoryController extends AbstractController<Manager, UserStory> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerUserStoryListService			listService;

	@Autowired
	protected ManagerUserStoryListByProyectService	listByProyectService;

	@Autowired
	protected ManagerUserStoryShowService			showService;

	@Autowired
	protected ManagerUserStoryCreateService			createService;

	@Autowired
	protected ManagerUserStoryUpdateService			updateService;

	@Autowired
	protected ManagerUserStoryDeleteService			deleteService;

	@Autowired
	protected ManagerUserStoryPublishService		publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("list-by-proyect", "list", this.listByProyectService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
