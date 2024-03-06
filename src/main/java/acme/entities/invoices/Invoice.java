
package acme.entities.invoices;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
import acme.entities.sponsorships.Sponsorship;
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
	@Column(unique = true)
	protected String			code;

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				registrationTime;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				dueDate;

	@Positive
	@Min(0)
	@NotNull
	protected Double			quantity;

	@Positive
	@DecimalMin(value = "0.0")
	@NotNull
	protected Double			tax;

	@URL
	protected String			infoLink;


	@Transient
	public Double getTotalAmount() {
		return this.quantity + this.tax;
	}

	// Relationships ----------------------------------------------------------


	@ManyToOne(optional = false)
	@NotNull
	@Valid
	private Sponsorship sponsorship;
}
