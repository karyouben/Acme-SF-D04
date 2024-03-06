
package acme.entities.sponsorships;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
	protected LocalDateTime		moment;

	protected LocalDateTime		duration;

	@Column(nullable = false)
	@Positive
	protected Double			amount;

	@Enumerated(EnumType.STRING)
	protected SponsorshipType	typeOfSponsorship; // Assuming SponsorshipType is an enum with "Financial" and "In kind"

	@Email
	protected String			contactEmail;

	@URL
	protected String			infoLink;

	// Constructors, getters, and setters


	public enum SponsorshipType {
		FINANCIAL, IN_KIND
	}


	// Ensure duration is at least one month after the moment
	@PrePersist
	@PreUpdate
	private void validateDuration() {
		if (this.duration.isBefore(this.moment.plusMonths(1)))
			throw new IllegalArgumentException("Duration must be at least one month after the moment");
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne
	@NotNull
	@Valid
	protected Project project;

}
