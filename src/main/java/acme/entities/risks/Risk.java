
/*
 *
 * Copyright (C) 2012-2024 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.entities.risks;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Risk extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Pattern(regexp = "R-\\d{3}")
	@Column(unique = true)
	protected String			reference;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@Past
	private Date				identificationDate;

	// Custom validation, the impact will be between 0,0 and 100,0 (at the moment)
	@NotNull
	@Positive
	@Range(min = 0, max = 1)
	@Digits(integer = 3, fraction = 2)
	protected Double			impact;

	// Custom validation, the probability will be calculated within the range of 0,0 to 1,0
	@NotNull
	@Positive
	@Range(min = 0, max = 1)
	@Digits(integer = 3, fraction = 2)
	protected Double			probability;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@URL
	@Length(max = 255)
	protected String			link;

	// Derived attributes -----------------------------------------------------


	@NotNull
	@Transient
	public Double getValue() {
		return this.impact * this.probability;
	}

	// Relationships -------------------------------------------------------------


	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Project project;

}
