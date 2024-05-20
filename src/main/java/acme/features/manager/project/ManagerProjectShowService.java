
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;
import acme.roles.Manager;

@Service
public class ManagerProjectShowService extends AbstractService<Manager, Project> {
	//Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerProjectRepository repository;

	//AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		Project project = this.repository.findProjectById(id);

		final Principal principal = super.getRequest().getPrincipal();
		final int userAccountId = principal.getAccountId();

		final boolean authorise = project != null && project.getManager().getUserAccount().getId() == userAccountId;

		super.getResponse().setAuthorised(authorise);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		Project project = this.repository.findProjectById(id);

		super.getBuffer().addData(project);
	}

	@Override
	public void unbind(final Project object) {
		assert object != null;

		Dataset dataset = super.unbind(object, "code", "title", "projectAbstract", "link", "totalCost", "draftMode", "hasErrors");

		//		if (object.isHasErrors()) {
		//			final Locale local = super.getRequest().getLocale();
		//			dataset.put("hasErrors", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		//		} else
		//			dataset.put("hasErrors", "No");

		super.getResponse().addData(dataset);
	}
}
