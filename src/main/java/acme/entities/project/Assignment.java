
package acme.entities.project;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Assignment extends AbstractEntity {
	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Relationships -------------------------------------------------------------

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	protected Project			project;

	@Valid
	@ManyToOne(optional = false)
	@NotNull
	protected UserStory			userStory;

}
