
package acme.features.sponsor.dashboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.datatypes.Statistics;
import acme.forms.SponsorDashboard;
import acme.roles.Sponsor;

@Service
public class SponsorDashboardShowService extends AbstractService<Sponsor, SponsorDashboard> {
	// Internal state ---------------------------------------------------------

	@Autowired
	protected SponsorDashboardRepository repository;

	// AbstractService interface ----------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		final SponsorDashboard dashboard = new SponsorDashboard();

		Principal principal;
		int userAccountId;
		principal = super.getRequest().getPrincipal();
		userAccountId = principal.getAccountId();
		final Sponsor sponsor = this.repository.findOneSponsorByUserAccountId(userAccountId);

		//SponsorshipCostStats
		final double averageSponsorshipCost = this.repository.findAverageSponsorshipCost(sponsor).orElse(0.0);
		final double maxSponsorshipCost = this.repository.findMaxSponsorshipCost(sponsor).orElse(0.0);
		final double minSponsorshipCost = this.repository.findMinSponsorshipCost(sponsor).orElse(0.0);
		final double devSponsorshipCost = this.repository.findLinearDevSponsorshipCost(sponsor).orElse(0.0);
		final Statistics sponsorshipAmountStats = new Statistics();
		sponsorshipAmountStats.setAverage(averageSponsorshipCost);
		sponsorshipAmountStats.setMinimum(minSponsorshipCost);
		sponsorshipAmountStats.setMaximum(maxSponsorshipCost);
		sponsorshipAmountStats.setDeviation(devSponsorshipCost);
		dashboard.setSponsorshipAmountStats(sponsorshipAmountStats);

		//InvoiceQuantityStats
		final double averageInvoiceQuantity = this.repository.findAverageInvoiceQuantity(sponsor).orElse(0.0);
		final double maxInvoiceQuantity = this.repository.findMaxInvoiceQuantity(sponsor).orElse(0.0);
		final double minInvoiceQuantity = this.repository.findMinInvoiceQuantity(sponsor).orElse(0.0);
		final double devInvoiceQuantity = this.repository.findLinearDevInvoiceQuantity(sponsor).orElse(0.0);
		final Statistics invoicesQuantityStats = new Statistics();
		invoicesQuantityStats.setAverage(averageInvoiceQuantity);
		invoicesQuantityStats.setMinimum(minInvoiceQuantity);
		invoicesQuantityStats.setMaximum(maxInvoiceQuantity);
		invoicesQuantityStats.setDeviation(devInvoiceQuantity);
		dashboard.setInvoicesQuantityStats(invoicesQuantityStats);

		final Integer numInvoicesWithTaxLessOrEqualThan21 = this.repository.findNumOfInvoicesWithTax21less(sponsor).orElse(0);
		final Integer numSponsorshipsWithLink = this.repository.findNumOfSponsorshipsWithLink(sponsor).orElse(0);
		dashboard.setNumInvoicesWithTaxLessOrEqualThan21(numInvoicesWithTaxLessOrEqualThan21);
		dashboard.setNumSponsorshipsWithLink(numSponsorshipsWithLink);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final SponsorDashboard object) {
		Dataset dataset;

		dataset = super.unbind(object, "sponsorshipAmountStats", "invoicesQuantityStats", "numInvoicesWithTaxLessOrEqualThan21", "numSponsorshipsWithLink");

		super.getResponse().addData(dataset);
	}
}
