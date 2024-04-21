
package acme.entities.contract;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.entities.project.Project;
import acme.roles.client.Client;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Contract extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	@NotBlank
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{1,3}-[0-9]{3}$")
	protected String			code;

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	protected Date				instantiation;

	@Length(max = 75)
	@NotBlank
	protected String			providerName;

	@Length(max = 75)
	@NotBlank
	protected String			customerName;

	@Length(max = 100)
	@NotBlank
	protected String			goals;

	private boolean				draftMode;

	@Valid
	@ManyToOne
	@JoinColumn(name = "project_id", nullable = false)
	private Project				project;

	@Valid
	@ManyToOne
	@JoinColumn(name = "client_id", nullable = false)
	private Client				client;

	//Custom restriction buget must be less than the project cost, that will be implemented on services, on future deliverable
	@NotNull
	private Money				budget;

}
