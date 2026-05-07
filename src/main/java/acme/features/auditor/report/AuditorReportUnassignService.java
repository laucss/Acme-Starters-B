
package acme.features.auditor.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audits.Report;
import acme.realms.Auditor;

@Service
public class AuditorReportUnassignService extends AbstractService<Auditor, Report> {

	//Internal state
	@Autowired
	private AuditorReportRepository	repository;
	private Report					auditReport;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.auditReport = this.repository.findReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int auditorId, auditReportId;
		Report aud;
		auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		auditReportId = super.getRequest().getData("id", int.class);
		aud = this.repository.findReportById(auditReportId);
		status = aud != null && aud.getAuditor().getId() == auditorId && MomentHelper.getCurrentMoment().before(aud.getProjectUnassignMoment());
		;

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.auditReport);
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReport);
	}

	@Override
	public void execute() {
		this.auditReport.setProject(null);
		this.auditReport.setProjectUnassignMoment(null);
		this.repository.save(this.auditReport);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.auditReport, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode", "monthsActive", "hours", "auditor");

	}

}
