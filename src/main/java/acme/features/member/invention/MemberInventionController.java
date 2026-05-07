
package acme.features.member.invention;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.inventions.Invention;
import acme.realms.Member;

@Controller
public class MemberInventionController extends AbstractController<Member, Invention> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", MemberInventionListService.class);
		super.addBasicCommand("show", MemberInventionShowService.class);
		// super.addBasicCommand("create", FundraiserStrategyCreateService.class);
		// super.addBasicCommand("update", FundraiserStrategyUpdateService.class);
		// super.addBasicCommand("delete", FundraiserStrategyDeleteService.class);

		// super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
