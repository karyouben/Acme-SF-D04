
package acme.forms;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboards extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of userStory with each Priority

	private Integer				totalMustUserStories;
	private Integer				totalShouldUserStories;
	private Integer				totalCouldUserStories;
	private Integer				totalWontUserStories;

	//	Average, deviation, minimum, and maximum estimated cost of the user stories
	private Statistics			userStoryCostStatistics;

	//	Average, deviation, minimum, and maximum estimated cost of the project
	private Statistics			projectCostStatistics;

}
