
package acme.entities.project;

import javax.persistence.ManyToOne;
import javax.persistence.Entity;
import javax.validation.Valid;

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
	protected Project			project;

	@Valid
	@ManyToOne(optional = false)
	protected UserStory			userStory;

}
