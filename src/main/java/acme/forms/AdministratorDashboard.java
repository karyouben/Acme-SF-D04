
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdministratorDashboard extends AbstractForm {
	// Serialisation identifier -----------------------------------------------

	protected static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of principals with each role.
	private Map<String, Integer>	totalUsers;

	// ratio of notices with both an email address and a link;
	private Double					linkAndEmailNoticesRatio;

	// ratios of critical and non-critical objectives
	private Double					criticalAndNonCriticalObjetives;

	// average, minimum, maximum, and standard deviation of the value in the risks; 
	private Map<String, Statistics>	risk;

	// average, minimum, maximum, and standard deviation of the number of claims posted over the last 10 weeks
	private Statistics				claimsInLast10WeeksData;

}
