
package acme.features.administrator.administratordashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Statistics;
import acme.forms.AdministratorDashboard;

@Service
public class AdministratorDashboardShowService extends AbstractService<Administrator, AdministratorDashboard> {

	// Internal state ---------------------------------------------------------
	@Autowired
	protected AdministratorDashboardRepository repository;

	// AbstractService Interface ----------------------------------------------


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		Integer totalAdministrators = this.repository.totalAdministrators();
		Integer totalAuditors = this.repository.totalAuditors();
		Integer totalConsumers = this.repository.totalConsumers();
		Integer totalDevelopers = this.repository.totalDevelopers();
		Integer totalManagers = this.repository.totalManagers();
		Integer totalProviders = this.repository.totalProviders();
		Integer totalSponsors = this.repository.totalSponsors();
		Integer totalClients = this.repository.totalClients();

		Double averageRiskValue = this.repository.findAverageRiskValue();
		Double deviationRiskValue = this.repository.findDeviationRiskValue();
		Double minimumRiskValue = this.repository.findMinimumRiskValue();
		Double maximumRiskValue = this.repository.findMaximumRiskValue();

		averageRiskValue = averageRiskValue != null ? averageRiskValue : 0.0;
		deviationRiskValue = deviationRiskValue != null ? deviationRiskValue : 0.0;
		minimumRiskValue = minimumRiskValue != null ? minimumRiskValue : 0.0;
		maximumRiskValue = maximumRiskValue != null ? maximumRiskValue : 0.0;

		Double averageClaimPosted10 = this.repository.findAverageClaimPosted10();
		Double deviationClaimPosted10 = this.repository.findDeviationClaimPosted10();
		Double minimumClaimPosted10 = this.repository.findMinimumClaimPosted10();
		Double maximumClaimPosted10 = this.repository.findMaximumClaimPosted10();

		averageClaimPosted10 = averageClaimPosted10 != null ? averageClaimPosted10 : 0.0;
		deviationClaimPosted10 = deviationClaimPosted10 != null ? deviationClaimPosted10 : 0.0;
		minimumClaimPosted10 = minimumClaimPosted10 != null ? minimumClaimPosted10 : 0.0;
		maximumClaimPosted10 = maximumClaimPosted10 != null ? maximumClaimPosted10 : 0.0;

		double totalNoticeWithEmailAndLink = this.repository.totalNoticeWithEmailAndLink();
		double totalNotice = this.repository.totalNotice();
		Double linkAndEmailNoticesRatio = totalNotice != 0.0 ? totalNoticeWithEmailAndLink / totalNotice : 0.0;

		double totalCriticalObjectives = this.repository.totalCriticalObjectives();
		double totalObjectives = this.repository.totalObjectives();
		Double criticalObjectivesRatio = totalObjectives != 0.0 ? totalCriticalObjectives / totalObjectives : 0.0;

		double totalNonCriticalObjectives = this.repository.totalNonCriticalObjectives();
		Double nonCriticalObjectivesRatio = totalObjectives != 0.0 ? totalNonCriticalObjectives / totalObjectives : 0.0;

		final Statistics riskValueStatistics = new Statistics();
		riskValueStatistics.setAverage(averageRiskValue);
		riskValueStatistics.setDeviation(deviationRiskValue);
		riskValueStatistics.setMaximum(maximumRiskValue);
		riskValueStatistics.setMinimum(minimumRiskValue);

		final Statistics claimPosted10Statistics = new Statistics();
		claimPosted10Statistics.setAverage(averageClaimPosted10);
		claimPosted10Statistics.setDeviation(deviationClaimPosted10);
		claimPosted10Statistics.setMaximum(maximumClaimPosted10);
		claimPosted10Statistics.setMinimum(minimumClaimPosted10);

		final AdministratorDashboard dashboard = new AdministratorDashboard();

		dashboard.setLinkAndEmailNoticesRatio(linkAndEmailNoticesRatio);
		dashboard.setCriticalObjectivesRatio(criticalObjectivesRatio);
		dashboard.setNonCriticalObjectivesRatio(nonCriticalObjectivesRatio);

		dashboard.setTotalAdministrators(totalAdministrators);
		dashboard.setTotalAuditors(totalAuditors);
		dashboard.setTotalConsumers(totalConsumers);
		dashboard.setTotalDevelopers(totalDevelopers);
		dashboard.setTotalManagers(totalManagers);
		dashboard.setTotalProviders(totalProviders);
		dashboard.setTotalSponsors(totalSponsors);
		dashboard.setTotalClients(totalClients);
		dashboard.setRiskValueStatistics(riskValueStatistics);
		dashboard.setClaimPosted10Statistics(claimPosted10Statistics);

		super.getBuffer().addData(dashboard);

	}

	@Override
	public void unbind(final AdministratorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, //
			"linkAndEmailNoticesRatio", "criticalObjectivesRatio", // 
			"nonCriticalObjectivesRatio", "totalAdministrators", "totalAuditors", // 
			"totalConsumers", "totalDevelopers", "totalManagers", // 
			"totalProviders", "totalSponsors", "totalClients", // 
			"riskValueStatistics", "claimPosted10Statistics");

		super.getResponse().addData(dataset);
	}

}
