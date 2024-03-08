
package acme.forms;

import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard {

	// Serialisation identifier ----------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------

	int							totalTrainingModulesWithUpdateMoment;
	int							totalTrainingSessionsWithLink;

	private Statistics			TrainingModulesTime;

}
