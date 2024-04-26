
package acme.forms;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of invoices with a tax less than or equal to 21.00%
	protected Integer			numInvoicesWithTaxLessOrEqualThan21;

	// Total number of sponsorships with a link
	protected Integer			numSponsorshipsWithLink;

	// Statistics for the amount of the sponsorships
	protected Statistics		sponsorshipAmountStats;

	// Statistics for the quantity of the invoices
	protected Statistics		invoicesQuantityStats;

}
