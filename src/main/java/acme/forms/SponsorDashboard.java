
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.datatypes.Statistics;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long			serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of invoices with a tax less than or equal to 21.00%
	protected Integer					totalNumberInvoicesTax21orLess;

	// Total number of sponsorships with a link
	protected Integer					totalNumberSponsorshipsWithLink;

	// Statistics for the amount of the sponsorships
	protected Map<String, Statistics>	sponsorshipAmountstatistics;

	// Statistics for the quantity of the invoices
	protected Map<String, Statistics>	invoiceQuanitityStatistics;

}
