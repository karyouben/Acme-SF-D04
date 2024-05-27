
package acme.features.administrator.administratordashboard;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Administrator;
import acme.client.data.models.Dataset;
import acme.client.helpers.MomentHelper;
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

		Integer minNumberOfClaims;
		Integer maxNumberOfClaims;
		Double stdNumberOfClaims;

		Date startDate;
		Date endDate;
		List<Integer> numberOfClaimsPerWeek;

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

		endDate = MomentHelper.getCurrentMoment();
		numberOfClaimsPerWeek = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			startDate = MomentHelper.deltaFromMoment(endDate, -1, ChronoUnit.WEEKS);
			numberOfClaimsPerWeek.add(this.repository.numberOfClaimsBetweenDates(startDate, endDate).intValue());
			endDate = startDate;
		}

		Double avgNumberOfClaims = numberOfClaimsPerWeek.stream().mapToDouble(x -> x).average().orElse(0.);
		minNumberOfClaims = numberOfClaimsPerWeek.stream().mapToInt(x -> x).min().orElse(0);
		maxNumberOfClaims = numberOfClaimsPerWeek.stream().mapToInt(x -> x).max().orElse(0);
		stdNumberOfClaims = numberOfClaimsPerWeek.stream().mapToDouble(x -> x).map(x -> Math.pow(x - avgNumberOfClaims, 2) / 2).sum();

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
		dashboard.setAvgNumberOfClaims(avgNumberOfClaims);
		dashboard.setMinNumberOfClaims(minNumberOfClaims);
		dashboard.setMaxNumberOfClaims(maxNumberOfClaims);
		dashboard.setStdNumberOfClaims(stdNumberOfClaims);

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
			"riskValueStatistics", "avgNumberOfClaims", "minNumberOfClaims", //
			"maxNumberOfClaims", "stdNumberOfClaims");

		super.getResponse().addData(dataset);
	}

}
