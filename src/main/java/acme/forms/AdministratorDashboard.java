
package acme.forms;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of principals with each role.
	private int					totalAdministrators;
	private int					totalAuditors;
	private int					totalConsumers;
	private int					totalDevelopers;
	private int					totalManagers;
	private int					totalProviders;
	private int					totalSponsors;
	private int					totalClients;

	// ratio of notices with both an email address and a link;
	private Double				linkAndEmailNoticesRatio;

	// ratios of critical and non-critical objectives
	private Double				criticalObjectivesRatio;
	private Double				nonCriticalObjectivesRatio;

	// average, minimum, maximum, and standard deviation of the value in the risks; 
	private Statistics			riskValueStatistics;

	// average, minimum, maximum, and standard deviation of the number of claims posted over the last 10 weeks
	private Statistics			claimPosted10Statistics;

	private Double				avgNumberOfClaims;
	private Integer				minNumberOfClaims;
	private Integer				maxNumberOfClaims;
	private Double				stdNumberOfClaims;

}
