
package acme.entities.sponsorships;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Sponsorship extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@NotBlank
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	@Column(unique = true)
	protected String			code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				moment;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				durationStart;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	private Date				durationEnd;

	@NotNull
	@Positive
	protected Double			amount;

	@Enumerated(EnumType.STRING)
	@NotNull
	protected SponsorshipType	typeOfSponsorship;

	@Email
	protected String			contactEmail;

	@URL
	protected String			infoLink;

	// Relationships ----------------------------------------------------------

	@ManyToOne(optional = false)
	@NotNull
	@Valid
	protected Project			project;

}
