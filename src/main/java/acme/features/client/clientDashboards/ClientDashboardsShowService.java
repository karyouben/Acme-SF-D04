
package acme.features.client.clientDashboards;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Statistics;
import acme.forms.ClientDashboard;
import acme.roles.client.Client;

@Service
public class ClientDashboardsShowService extends AbstractService<Client, ClientDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ClientDashboardsRepository repository;

	// AbstractService Interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Client client = this.repository.findClientById(id);
		status = client != null && principal.hasRole(Client.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		Integer totalNumProgressLogLessThan25 = this.repository.totalNumProgressLogLessThan25(userAccountId);
		Integer totalNumProgressLogLessBetween25And50 = this.repository.totalNumProgressLogLessBetween25And50(userAccountId);
		Integer totalNumProgressLogLessBetween50And75 = this.repository.totalNumProgressLogLessBetween50And75(userAccountId);
		Integer totalNumProgressLogAbove75 = this.repository.totalNumProgressLogAbove75(userAccountId);

		Double findAverageContractBudget = this.repository.findAverageContractBudget(userAccountId);
		Double findDeviationContractBudget = this.repository.findDeviationContractBudget(userAccountId);
		Double findMaximumContractBudget = this.repository.findMaximumContractBudget(userAccountId);
		Double findMinimumContractBudget = this.repository.findMinimumContractBudget(userAccountId);

		final Statistics contractTimeStatistics = new Statistics();
		contractTimeStatistics.setAverage(findAverageContractBudget);
		contractTimeStatistics.setDeviation(findDeviationContractBudget);
		contractTimeStatistics.setMaximum(findMaximumContractBudget);
		contractTimeStatistics.setMinimum(findMinimumContractBudget);

		final ClientDashboard dashboard = new ClientDashboard();

		dashboard.setTotalNumProgressLogLessThan25(totalNumProgressLogLessThan25);
		dashboard.setTotalNumProgressLogLessBetween25And50(totalNumProgressLogLessBetween25And50);
		dashboard.setTotalNumProgressLogLessBetween50And75(totalNumProgressLogLessBetween50And75);
		dashboard.setTotalNumProgressLogAbove75(totalNumProgressLogAbove75);
		dashboard.setContractTimeStatistics(contractTimeStatistics);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ClientDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalNumProgressLogLessThan25", "totalNumProgressLogLessBetween25And50", // 
			"totalNumProgressLogLessBetween50And75", "totalNumProgressLogAbove75", "contractTimeStatistics");

		super.getResponse().addData(dataset);
	}

}
