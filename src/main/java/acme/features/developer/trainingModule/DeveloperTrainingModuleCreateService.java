
package acme.features.developer.trainingModule;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.trainingModule.DifficultyLevel;
import acme.entities.trainingModule.TrainingModule;
import acme.roles.Developer;

@Service
public class DeveloperTrainingModuleCreateService extends AbstractService<Developer, TrainingModule> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private DeveloperTrainingModuleRepository repository;

	// AbstractService<Authenticated, TrainingModule> ---------------------------


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final int id = super.getRequest().getPrincipal().getActiveRoleId();

		TrainingModule object = new TrainingModule();
		object.setDeveloper(this.repository.findDeveloperById(id));
		object.setDraftMode(true);

		super.getBuffer().addData(object);
	}

	@Override
	public void bind(final TrainingModule object) {
		assert object != null;

		int projectId = super.getRequest().getData("project", int.class);
		Project project = this.repository.findProjectById(projectId);

		super.bind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project");

		Date currentMoment = MomentHelper.getCurrentMoment();
		Date creationMoment = new Date(currentMoment.getTime() - 600000); //Substracts 9 minutes to ensure the moment is in the past

		object.setCreationMoment(creationMoment);
		object.setProject(project);
	}

	@Override
	public void validate(final TrainingModule object) {
		assert object != null;

		if (!super.getBuffer().getErrors().hasErrors("code")) {
			final boolean duplicatedCode = this.repository.findAllTrainingModules().stream().anyMatch(e -> e.getCode().equals(object.getCode()));

			super.state(!duplicatedCode, "code", "developer.trainingModule.form.error.duplicated-code");
		}
	}

	@Override
	public void perform(final TrainingModule object) {
		assert object != null;

		this.repository.save(object);
	}

	@Override
	public void unbind(final TrainingModule object) {
		assert object != null;

		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		SelectChoices choices = SelectChoices.from(DifficultyLevel.class, object.getDifficultyLevel());

		Dataset dataset = super.unbind(object, "code", "creationMoment", "details", "difficultyLevel", "updateMoment", "link", "totalTime", "project", "draftMode");

		dataset.put("project", projectsChoices.getSelected().getKey());
		dataset.put("projects", projectsChoices);
		dataset.put("difficultyLevelOptions", choices);

		super.getResponse().addData(dataset);
	}

}
