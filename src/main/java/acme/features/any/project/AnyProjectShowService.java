
package acme.features.any.project;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Any;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.project.Project;

@Service
public class AnyProjectShowService extends AbstractService<Any, Project> {
	//Internal state ---------------------------------------------------------

	@Autowired
	protected AnyProjectRepository repository;

	//AbstractService<Authenticated, Consumer> ---------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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

		Dataset dataset = super.unbind(object, "code", "title", "abstract$", "link", "totalCost", "draftMode");

		if (object.isHasErrors()) {
			final Locale local = super.getRequest().getLocale();
			dataset.put("hasErrors", local.equals(Locale.ENGLISH) ? "Yes" : "Sí");
		} else
			dataset.put("hasErrors", "No");

		super.getResponse().addData(dataset);
	}

}
