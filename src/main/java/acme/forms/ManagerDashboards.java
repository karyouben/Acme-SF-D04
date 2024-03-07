
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboards extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long		serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of userStory with each Priority
	private Map<String, Integer>	totalPriorities;

	//	Average, deviation, minimum, and maximum estimated cost of the user stories
	private Map<String, Statistic>	costOfUserStories;

	//	Average, deviation, minimum, and maximum estimated cost of the project

	private Map<String, Statistic>	costOfProjects;

}
