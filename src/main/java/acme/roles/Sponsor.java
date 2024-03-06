
package acme.roles;

import javax.persistence.Entity;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractRole;

@Entity
public class Sponsor extends AbstractRole {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@NotNull
	@Size(max = 75)
	private String				name;

	@NotBlank
	@Size(max = 100)
	private String				benefits;

	@URL
	private String				webpage;

	@Email
	private String				contactEmail;

	// Constructors, getters, and setters
}
