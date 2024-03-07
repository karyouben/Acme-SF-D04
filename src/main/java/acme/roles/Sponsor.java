
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;

@Entity
public class Sponsor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	private String				name;

	@NotBlank
	@Length(max = 100)
	private String				benefits;

	@URL
	private String				webPage;

	@Email
	private String				contactEmail;

	// Constructors, getters, and setters
}
