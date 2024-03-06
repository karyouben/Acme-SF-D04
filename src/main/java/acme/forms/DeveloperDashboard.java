
package acme.forms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeveloperDashboard {

	// Serialisation identifier ----------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes ----------------------------------

	Integer						totalTrainingModulesWithUpdateMoment;
	Integer						totalTrainingSessionsWithLink;

	Double						averageTimeTrainingModules;
	Double						deviatonTimeTrainingModules;
	Double						minimumTimeTrainingModules;
	Double						maximumTimeTrainingModules;

	//private Statistic			TrainingModulesTime;

}
