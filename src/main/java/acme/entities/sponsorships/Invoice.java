
package acme.entities.sponsorships;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Invoice extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@NotNull
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	protected String			code;

	@Past
	@NotNull
	protected LocalDateTime		registrationTime;

	@NotNull
	protected LocalDateTime		dueDate;

	@Positive
	@Min(0)
	protected Double			quantity;

	@Positive
	@DecimalMin(value = "0.0")
	protected Double			tax;

	@URL
	protected String			infoLink;


	@Transient
	public Double getTotalAmount() {
		return this.quantity + this.tax;
	}
	//1 mes despues del registro
	@PrePersist
	@PreUpdate
	protected void validateDueDate() {
		if (this.dueDate.isBefore(this.registrationTime.plusMonths(1)))
			throw new IllegalArgumentException("Due date must be at least one month ahead of the registration time");
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne
	@NotNull
	@Valid
	private Sponsorship sponsorship;
}
