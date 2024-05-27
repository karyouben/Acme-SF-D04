
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------

	private int					totalNumberInvoicesTaxEqualOrLessThan21;
	private int					totalNumberSponsorshipsWithLink;

	private Double				averageAmountSponsorships;
	private Double				stdevAmountSponsorships;
	private Double				minimumAmountSponsorships;
	private Double				maximumAmountSponsorships;

	private Double				averageQuantityInvoices;
	private Double				stdevQuantityInvoices;
	private Double				minimumQuantityInvoices;
	private Double				maximumQuantityInvoices;

}
