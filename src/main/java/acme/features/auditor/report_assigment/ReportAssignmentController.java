package acme.features.auditor.report_assigment;




import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.ReportAssignment;
import acme.realms.Auditor;

@Controller
public class ReportAssignmentController extends AbstractController<Auditor, ReportAssignment> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("create", ReportAssignmentCreateService.class);
	}
}