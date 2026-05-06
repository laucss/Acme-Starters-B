
package acme.features.any.report;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.audits.Report;
import acme.realms.Auditor;

@Service
public class AnyReportListService extends AbstractService<Any, Report> {

	@Autowired
	private AnyReportRepository	repository;

	private Collection<Report>	reports;


	@Override
	public void load() {
		Integer projectId = null;

		if (super.getRequest().hasData("projectId"))
			projectId = super.getRequest().getData("projectId", Integer.class);

		if (projectId != null)
			this.reports = this.repository.findReportsByProjectId(projectId);
		else
			this.reports = this.repository.findPublishedReports();

	}

	@Override
	public void authorise() {
		boolean status = true;

		if (super.getRequest().hasData("projectId")) {
			int projectId = super.getRequest().getData("projectId", int.class);

			var project = this.repository.findProjectById(projectId);

			status = project != null && !project.getDraftMode();
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Integer projectId = super.getRequest().hasData("projectId") ? super.getRequest().getData("projectId", Integer.class) : null;
		boolean isAuditor = super.getRequest().getPrincipal().hasRealmOfType(Auditor.class);
		super.getResponse().addGlobal("projectId", projectId);
		super.getResponse().addGlobal("isAuditor", isAuditor);
		super.getResponse().addData(super.unbindObjects(this.reports, "ticker", "name", "monthsActive", "hours"));
	}

}
