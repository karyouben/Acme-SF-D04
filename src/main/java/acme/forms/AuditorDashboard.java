
package acme.forms;

import java.util.Map;

import acme.client.data.AbstractForm;
import acme.entities.codeAudits.Type;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class AuditorDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	private Map<Type, Integer>	numAuditsPerType;

	private Double				averageNumAuditRecords;
	private Double				deviationNumAuditRecords;
	private int					minNumAuditRecords;
	private int					maxNumAuditRecords;

	private Double				averagePeriodLength;
	private Double				deviationPeriodLength;
	private Double				minPeriodLength;
	private Double				maxPeriodLength;

}
