
package acme.features.auditor.auditRecord;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.auditRecords.AuditRecord;
import acme.roles.Auditor;

@Controller
public class AuditorAuditRecordController extends AbstractController<Auditor, AuditRecord> {

	//Internal state ------------------------------------------
	@Autowired
	private AuditorAuditRecordCreateService				createService;

	@Autowired
	private AuditorAuditRecordDeleteService				deleteService;

	@Autowired
	private AuditorAuditRecordListForCodeAuditsService	listForCodeAuditsService;

	@Autowired
	private AuditorAuditRecordListMineService			listMineService;

	@Autowired
	private AuditorAuditRecordPublishService			publishService;

	@Autowired
	private AuditorAuditRecordShowService				showService;

	@Autowired
	private AuditorAuditRecordUpdateService				updateService;


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);
		super.addCustomCommand("list-for-code-audits", "list", this.listForCodeAuditsService);
		super.addCustomCommand("list-mine", "list", this.listMineService);
		super.addCustomCommand("publish", "update", this.publishService);
		super.addBasicCommand("show", this.showService);
	}

}
