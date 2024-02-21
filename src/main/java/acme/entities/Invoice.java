
package acme.entities;

import java.time.LocalDateTime;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "invoices")
public class Invoice {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID			id;

	@NotBlank
	@Column(nullable = false, unique = true)
	@Pattern(regexp = "IN-[0-9]{4}-[0-9]{4}")
	private String			code;

	@NotNull
	@Past
	@Column(nullable = false)
	private LocalDateTime	registrationTime;

	@NotNull
	@Column(nullable = false)
	private LocalDateTime	dueDate;

	@NotNull
	@Positive
	@Column(nullable = false)
	private Double			quantity;

	@NotNull
	@PositiveOrZero
	@Column(nullable = false)
	private Double			tax;

	@NotNull
	@Positive
	@Column(nullable = false)
	private Double			totalAmount;

	@URL
	private String			informationLink;

	@ManyToOne
	@JoinColumn(name = "sponsorship_id", nullable = false)
	private Sponsorship		sponsorship;


	@PrePersist
	@PreUpdate
	private void beforeSave() {
		this.validateDueDate();
		this.calculateTotalAmount();
	}

	//Validación personalizada para asegurar que la fecha de vencimiento sea al menos un mes después de la hora de registro.
	private void validateDueDate() {
		if (this.dueDate.isBefore(this.registrationTime.plusMonths(1)))
			throw new IllegalArgumentException("The due date must be at least one month ahead of the registration time.");
	}

	//Lógica personalizada para calcular el monto total antes de persistir o actualizar.
	private void calculateTotalAmount() {
		this.totalAmount = this.quantity + this.tax;
	}
}
