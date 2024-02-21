
package acme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "sponsorship")
public class Sponsorship {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID			id;

	@NotBlank
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "[A-Z]{1,3}-[0-9]{3}")
	private String			code;

	@NotNull
	@Past
	@Column(nullable = false)
	private LocalDateTime	moment;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime	duration;

	@NotNull
	@Positive
	@Column(nullable = false)
	private Double			amount;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private SponsorshipType	type;

	@Email
	private String			contactEmail;

	@URL
	private String			informationLink;


	public enum SponsorshipType {
		FINANCIAL, IN_KIND
	}


	// Una validación personalizada para asegurar que la duración es un mes después al momento
	@PrePersist
	@PreUpdate
	private void validateDuration() {
		if (this.duration.isBefore(this.moment.plusMonths(1)))
			throw new IllegalArgumentException("The duration must be at least one month after the moment.");
	}
}
