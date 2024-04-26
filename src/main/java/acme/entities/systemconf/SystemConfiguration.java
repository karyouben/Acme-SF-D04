
package acme.entities.systemconf;

import javax.persistence.Entity;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Range;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class SystemConfiguration extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}$")
	public String				systemCurrency;

	@NotBlank
	@Pattern(regexp = "^([A-Z]{3}(?:,|,\\s))*[A-Z]{3}$")
	public String				acceptedCurrencies;

	@NotBlank
	@Pattern(regexp = "^(\"[^\"]*\"|[^\",]+)(,(\"[^\"]*\"|[^\",]+))*$", message = "{validation.systemConfiguration.spamWords}")
	protected String			spamWords;

	@Range(min = 0, max = 1)
	protected double			spamThreshold;

}
