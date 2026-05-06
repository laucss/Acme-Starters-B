
package acme.features.sponsor.sponsorship_assignment;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.forms.SponsorshipAssignment;
import acme.realms.Sponsor;

@Controller
public class SponsorshipAssignmentController extends AbstractController<Sponsor, SponsorshipAssignment> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);
		super.addBasicCommand("create", SponsorshipAssignmentCreateService.class);
	}

}
