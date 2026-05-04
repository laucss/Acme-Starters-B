
package acme.features.manager.newMember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.UserAccount;
import acme.client.controllers.AbstractController;
import acme.realms.Manager;

@Controller
public class ManagerNewMemberController extends AbstractController<Manager, UserAccount> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ManagerNewMemberListService.class);
		// super.addBasicCommand("show", ManagerNewMemberListService.class);
		//super.addBasicCommand("create", ManagerProjectCreateService.class);
		//super.addBasicCommand("update", ManagerProjectUpdateService.class);
		//super.addBasicCommand("delete", ManagerProjectDeleteService.class);

		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
