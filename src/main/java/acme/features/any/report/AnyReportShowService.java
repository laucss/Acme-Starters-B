
package acme.features.any.report;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.audits.Report;

@Service
public class AnyReportShowService extends AbstractService<Any, Report> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyReportRepository	repository;

	private Report				report;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.report = this.repository.findReportById(id);
	}

	@Override
	public void authorise() {
		boolean status;

		status = this.report != null && !this.report.isDraftMode();

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.report, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "hours", "auditor");
		if (this.report.getProject() != null) {
			super.unbindGlobal("title", this.report.getProject().getTitle());
			if (this.report.getAuditor().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId()) {
				super.unbindGlobal("projectId", this.report.getProject().getId());
				if (this.report.getProjectUnassignMoment() != null && MomentHelper.getCurrentMoment().before(this.report.getProjectUnassignMoment()))
					super.unbindGlobal("projectUnassignMoment", true);
			}

		}
	}
}
