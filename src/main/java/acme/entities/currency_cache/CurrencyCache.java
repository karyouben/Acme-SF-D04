
package acme.entities.currency_cache;

import java.util.Date;

import javax.persistence.Entity;

import acme.client.data.AbstractEntity;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CurrencyCache extends AbstractEntity {

	protected static final long	serialVersionUID	= 1L;

	protected Date				date;

	protected double			ratio;

	protected String			origenCurrency;

	protected String			destinationCurrency;

}
