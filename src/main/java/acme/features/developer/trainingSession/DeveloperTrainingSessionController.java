
package acme.features.developer.trainingSession;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.trainingModule.TrainingSession;
import acme.roles.Developer;

@Controller
public class DeveloperTrainingSessionController extends AbstractController<Developer, TrainingSession> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected DeveloperTrainingSessionListService					listService;

	@Autowired
	protected DeveloperTrainingSessionListByTrainingModuleService	listByProyectService;

	@Autowired
	protected DeveloperTrainingSessionShowService					showService;

	@Autowired
	protected DeveloperTrainingSessionCreateService					createService;

	@Autowired
	protected DeveloperTrainingSessionUpdateService					updateService;

	@Autowired
	protected DeveloperTrainingSessionDeleteService					deleteService;

	@Autowired
	protected DeveloperTrainingSessionPublishService				publishService;

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
