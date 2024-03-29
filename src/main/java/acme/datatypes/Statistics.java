
package acme.datatypes;

import java.text.DecimalFormat;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {

	private DecimalFormat		formatter			= new DecimalFormat("#.##");

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	Double						average;

	Double						deviation;

	Double						minimum;

	Double						maximum;


	public String getAverageString() {
		return this.formatter.format(this.average);
	}
	public String getDeviationString() {
		return this.formatter.format(this.deviation);
	}
	public String getMinimumString() {
		return this.formatter.format(this.minimum);
	}
	public String getMaximumString() {
		return this.formatter.format(this.maximum);
	}

}
