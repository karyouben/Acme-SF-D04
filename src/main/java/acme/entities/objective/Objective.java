
package acme.entities.objective;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objective extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotNull
	@PastOrPresent
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiation;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@NotNull
	protected Priority			priority;

	private boolean				isCritical;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				startDurationPeriod;

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				endDurationPeriod;

	@URL
	@Length(max = 255)
	protected String			link;

	@Valid
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	protected Project			project;
}
