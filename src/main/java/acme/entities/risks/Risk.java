
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.accounts.Administrator;
import acme.entities.project.Project;

@Entity
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "R-[0-9]{3}")
	@Column(unique = true)
	protected String			reference;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Past
	protected Date				identificationDate;

	// Custom validation, the impact will be between 0,0 and 10,0 (at the moment)
	@NotNull
	@Min(0)
	protected Double			impact;

	// Custom validation, the probability will be calculated within the range of 0,0 to 1,0
	@NotNull
	@Min(0)
	protected Double			probability;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@URL
	protected String			infoLink;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Double value() {
		return this.impact * this.probability;
	}

	// Relationships -------------------------------------------------------------


	@NotNull
	@Valid
	@ManyToOne(optional = false)
	private Administrator	admin;

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Project		project;

}
