
package acme.features.administrator.risk;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.client.views.SelectChoices;
import acme.entities.project.Project;
import acme.entities.risks.Risk;

@Service
public class AdministratorRiskListService extends AbstractService<Administrator, Risk> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AdministratorRiskRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<Risk> objects;

		objects = this.repository.findAllRisks();

		super.getBuffer().addData(objects);
	}

	@Override
	public void unbind(final Risk object) {
		assert object != null;

		Dataset dataset;
		Collection<Project> projects = this.repository.findAllProjects();
		SelectChoices projectsChoices = SelectChoices.from(projects, "code", object.getProject());

		dataset = super.unbind(object, "reference", "identificationDate", "impact", "probability", "project");

		dataset.put("project", projectsChoices.getSelected().getLabel());
		dataset.put("projects", projectsChoices);

		super.getResponse().addData(dataset);
	}

}
