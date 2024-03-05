
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import acme.client.data.AbstractEntity;
import acme.client.data.datatypes.Money;
import acme.roles.Manager;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class UserStory extends AbstractEntity {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@NotBlank
	@Length(max = 75)
	protected String			title;

	@NotBlank
	@Length(max = 100)
	protected String			description;

	//Tenia int pero creo que es Money
	@NotNull
	protected Money				costPerHour;

	@NotBlank
	@Length(max = 100)
	protected String			acceptanceCriteria;

	@NotNull
	protected Priority			priority;

	@URL
	@Length(max = 255)
	protected String			link;

	// Relationships -------------------------------------------------------------

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	protected Manager			manager;

}
