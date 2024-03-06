
package acme.forms;

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

	Double						averageTimeTrainingModules;
	Double						deviatonTimeTrainingModules;
	Integer						minimumTimeTrainingModules;
	Integer						maximumTimeTrainingModules;

	//private Statistic			TrainingModulesTime;

}
