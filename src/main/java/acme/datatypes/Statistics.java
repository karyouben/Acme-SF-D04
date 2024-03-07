
package acme.datatypes;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Statistics {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	int							count;

	Double						average;

	Double						deviation;

	Double						minimum;

	Double						maximum;

}
