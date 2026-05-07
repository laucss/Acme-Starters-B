
package acme.features.member.campaign.spokesperson;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.realms.Member;
import acme.realms.Spokesperson;

@Controller
public class MemberCampaignSpokespersonController extends AbstractController<Member, Spokesperson> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", MemberCampaignSpokespersonShowService.class);
	}

}
