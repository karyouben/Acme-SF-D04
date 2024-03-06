
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SponsorDashboard extends AbstractForm {

	// Serialisation identifier -----------------------------------------------

	protected static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	// Total number of invoices with a tax less than or equal to 21.00%
	Integer						totalNumberInvoicesTax21orLess;

	// Total number of sponsorships with a link
	Integer						totalNumberSponsorshipsWithLink;

	// Statistics for the amount of the sponsorships
	Map<String, Double>			statisticsSponsorshipAmount;
	//	Double						averageSponsorshipAmount;
	//	Double						deviationSponsorshipAmount;
	//	Double						minimumSponsorshipAmount;
	//	Double						maximumSponsorshipAmount;

	// Statistics for the quantity of the invoices
	Map<String, Double>			statisticsInvoiceQuanitity;
	//	Double						averageInvoiceQuantity;
	//	Double						deviationInvoiceQuantity;
	//	Double						minimumInvoiceQuantity;
	//	Double						maximumInvoiceQuantity;
}
