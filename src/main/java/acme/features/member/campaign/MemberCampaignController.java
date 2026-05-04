
package acme.features.member.campaign;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.campaigns.Campaign;
import acme.realms.Member;

@Controller
public class MemberCampaignController extends AbstractController<Member, Campaign> {

	// Constructors -----------------------------------------------------------

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", MemberCampaignListService.class);
		// super.addBasicCommand("show", FundraiserStrategyShowService.class);
		// super.addBasicCommand("create", FundraiserStrategyCreateService.class);
		// super.addBasicCommand("update", FundraiserStrategyUpdateService.class);
		// super.addBasicCommand("delete", FundraiserStrategyDeleteService.class);

		// super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
