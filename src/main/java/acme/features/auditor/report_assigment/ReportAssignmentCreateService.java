
package acme.features.auditor.report_assigment;

import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audits.Report;
import acme.entities.projects.Project;
import acme.forms.ReportAssignment;
import acme.realms.Auditor;

@Service

public class ReportAssignmentCreateService extends AbstractService<Auditor, ReportAssignment> {

	@Autowired
	private ReportAssignmentRepository	repository;
	private ReportAssignment			auditReportAssigment;
	private Collection<Report>			auditreports;
	private Project						project;


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		int auditorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.auditreports = this.repository.findAvailableReportsByAuditorId(auditorId);
		this.auditReportAssigment = super.newObject(ReportAssignment.class);
		this.auditReportAssigment.setProjectId(projectId);

	}

	@Override
	public void authorise() {
		boolean status;
		status = this.project != null && !this.project.getDraftMode();
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int projectId = super.getRequest().getData("projectId", int.class);
		super.bindObject(this.auditReportAssigment, "auditReportId");
		this.auditReportAssigment.setProjectId(projectId);
	}

	@Override
	public void validate() {
		super.validateObject(this.auditReportAssigment);

		boolean hasAuditReport = this.auditReportAssigment.getAuditReportId() != 0;
		super.state(hasAuditReport, "auditReportId", "auditor.audit-report-assignment.error.auditReportId.required");
	}

	@Override
	public void execute() {
		Report report = this.repository.findReportById(this.auditReportAssigment.getAuditReportId());
		if (report != null) {
			Date projectUnassignMoment = MomentHelper.deltaFromCurrentMoment(24, ChronoUnit.HOURS);
			report.setProjectUnassignMoment(projectUnassignMoment);
			report.setProject(this.project);
			this.repository.save(report);
		}
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		choices = SelectChoices.from(this.auditreports, "ticker", null);
		super.unbindObject(this.auditReportAssigment, "auditReportId");
		super.unbindGlobal("listaAuditReports", choices);
		super.unbindGlobal("projectId", this.project.getId());
	}
}
