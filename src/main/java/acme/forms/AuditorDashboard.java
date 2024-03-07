
package acme.forms;

import acme.client.data.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private int					numStaticAudits;
	private int					numDynamicAudits;

	private Double				averageNumAuditRecords;
	private Double				deviationNumAuditRecords;
	private int					minNumAuditRecords;
	private int					maxNumAuditRecords;

	private Double				averagePeriodLength;
	private Double				deviationPeriodLength;
	private Double				minPeriodLength;
	private Double				maxPeriodLength;

}
