
package acme.entities.project;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Project extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "^[A-Z]{3}-\\d{4}$")
	protected String			code;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			abstract$;

	//Business rule: Projects containing fatal errors must be rejected by the system
	protected boolean			hasErrors;

	protected boolean			draftMode;

	@URL
	@Length(max = 255)
	protected String			link;

	@NotNull
	protected Money				totalCost;

	@NotNull
	protected Money				remainingCost;

	// Relationships -------------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Manager			manager;

}
