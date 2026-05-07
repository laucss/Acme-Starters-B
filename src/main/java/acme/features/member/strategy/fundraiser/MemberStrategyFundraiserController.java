
package acme.features.member.strategy.fundraiser;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.realms.Fundraiser;
import acme.realms.Member;

@Controller
public class MemberStrategyFundraiserController extends AbstractController<Member, Fundraiser> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", MemberStrategyFundraiserShowService.class);
	}

}
