
package acme.features.member.invention.inventor;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.realms.Inventor;
import acme.realms.Member;

@Controller
public class MemberInventionInventorController extends AbstractController<Member, Inventor> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("show", MemberInventionInventorShowService.class);
	}

}
