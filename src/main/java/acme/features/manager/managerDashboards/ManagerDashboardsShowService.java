
package acme.features.manager.managerDashboards;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Statistics;
import acme.entities.project.Priority;
import acme.forms.ManagerDashboards;
import acme.roles.Manager;

@Service
public class ManagerDashboardsShowService extends AbstractService<Manager, ManagerDashboards> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected ManagerDashboardsRepository repository;

	// AbstractService Interface ----------------------------------------------


	@Override
	public void authorise() {
		boolean status;

		Principal principal = super.getRequest().getPrincipal();
		int id = principal.getAccountId();
		Manager manager = this.repository.findManagerById(id);
		status = manager != null && principal.hasRole(Manager.class);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		final Principal principal = super.getRequest().getPrincipal();
		int userAccountId = principal.getAccountId();

		Integer totalMustUserStories = this.repository.totalUserStoriesByPriority(userAccountId, Priority.MUST);
		Integer totalShouldUserStories = this.repository.totalUserStoriesByPriority(userAccountId, Priority.SHOULD);
		Integer totalCouldUserStories = this.repository.totalUserStoriesByPriority(userAccountId, Priority.COULD);
		Integer totalWontUserStories = this.repository.totalUserStoriesByPriority(userAccountId, Priority.WONT);

		Double averageUserStoryCost = this.repository.findAverageUserStoryCost(userAccountId);
		Double deviationUserStoryCost = this.repository.findDeviationUserStoryCost(userAccountId);
		Double minimumUserStoryCost = this.repository.findMinimumUserStoryCost(userAccountId);
		Double maximumUserStoryCost = this.repository.findMaximumUserStoryCost(userAccountId);

		final Statistics userStoryCostStatistics = new Statistics();
		userStoryCostStatistics.setAverage(averageUserStoryCost);
		userStoryCostStatistics.setDeviation(deviationUserStoryCost);
		userStoryCostStatistics.setMaximum(maximumUserStoryCost);
		userStoryCostStatistics.setMinimum(minimumUserStoryCost);

		Map<String, Statistics> projectCostStatistics = new HashMap<>();

		for (String currency : this.repository.findSystemConfiguration().get(0).getAcceptedCurrencies().split(",")) {
			Double averageProjectCost = this.repository.findAverageProjectCost(userAccountId, currency);
			Double deviationProjectCost = this.repository.findDeviationProjectCost(userAccountId, currency);
			Double minimumProjectCost = this.repository.findMinimumProjectCost(userAccountId, currency);
			Double maximumProjectCost = this.repository.findMaximumProjectCost(userAccountId, currency);

			final Statistics statistics = new Statistics();
			statistics.setAverage(averageProjectCost);
			statistics.setDeviation(deviationProjectCost);
			statistics.setMaximum(maximumProjectCost);
			statistics.setMinimum(minimumProjectCost);

			projectCostStatistics.put(currency, statistics);
		}

		final ManagerDashboards dashboard = new ManagerDashboards();

		dashboard.setProjectCostStatistics(projectCostStatistics);
		dashboard.setUserStoryCostStatistics(userStoryCostStatistics);
		dashboard.setTotalCouldUserStories(totalCouldUserStories);
		dashboard.setTotalMustUserStories(totalMustUserStories);
		dashboard.setTotalShouldUserStories(totalShouldUserStories);
		dashboard.setTotalWontUserStories(totalWontUserStories);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboards object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"totalMustUserStories", "totalShouldUserStories", // 
			"totalCouldUserStories", "totalWontUserStories", //
			"userStoryCostStatistics", "projectCostStatistics");

		super.getResponse().addData(dataset);
	}
}
