
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
		try {
			return this.formatter.format(this.average);
		} catch (Exception e) {
			return "-";
		}
	}
	public String getDeviationString() {
		try {
			return this.formatter.format(this.deviation);
		} catch (Exception e) {
			return "-";
		}
	}
	public String getMinimumString() {
		try {
			return this.formatter.format(this.minimum);
		} catch (Exception e) {
			return "-";
		}
	}
	public String getMaximumString() {
		try {
			return this.formatter.format(this.maximum);
		} catch (Exception e) {
			return "-";
		}
	}

}
