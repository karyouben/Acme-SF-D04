
package acme.entities.objetive;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.entities.project.Project;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Objetive extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	@Temporal(TemporalType.TIMESTAMP)
	@Past
	@NotNull
	protected Date				instantiation;

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	@NotNull
	protected Priority			priority;

	protected boolean			isCritical;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				startDurationPeriod;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	protected Date				endDurationPeriod;

	@URL
	@Length(max = 255)
	protected String			link;

	@Valid
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project				project;

}
