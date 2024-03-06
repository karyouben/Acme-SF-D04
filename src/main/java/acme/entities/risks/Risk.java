
package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

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

	@NotNull
	@NotBlank
	@Pattern(regexp = "R-[0-9]{3}")
	@Column(unique = true)
	protected String			reference;

	@NotNull
	@Temporal(TemporalType.DATE)
	@Past
	protected Date				identificationDate;

	@NotNull
	@DecimalMin("0.0")
	@Positive
	protected Double			impact;

	//Asumiendo una probabilidad entre 0 y 1
	@NotNull
	@DecimalMin("0.0")
	@DecimalMax("1.0")
	@Positive
	protected Double			probability;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@URL
	protected String			infoLink;


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
