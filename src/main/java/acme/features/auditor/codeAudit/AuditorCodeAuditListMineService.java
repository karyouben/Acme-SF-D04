
package acme.features.auditor.codeAudit;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.data.accounts.Principal;
import acme.client.data.models.Dataset;
import acme.client.services.AbstractService;
import acme.entities.auditRecords.Mark;
import acme.entities.codeAudits.CodeAudit;
import acme.roles.Auditor;

@Service
public class AuditorCodeAuditListMineService extends AbstractService<Auditor, CodeAudit> {

	@Autowired
	private AuditorCodeAuditRepository repository;


	@Override
	public void authorise() {

		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<CodeAudit> codeAudits;
		Principal principal;
		principal = super.getRequest().getPrincipal();
		codeAudits = this.repository.findAllCodeAuditByAuditorId(principal.getActiveRoleId());

		super.getBuffer().addData(codeAudits);
	}

	@Override
	public void unbind(final CodeAudit object) {
		assert object != null;

		Dataset dataset;
		String markMode;

		Collection<Mark> marks = this.repository.findMarksByAuditId(object.getId());
		markMode = MarkMode.calculate(marks);
		dataset = super.unbind(object, "code", "execution", "type", "draftMode");
		dataset.put("markMode", markMode);

		super.getResponse().addData(dataset);
	}
}
