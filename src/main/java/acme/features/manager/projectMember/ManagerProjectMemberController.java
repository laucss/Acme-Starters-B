
package acme.features.manager.projectMember;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectMember;
import acme.realms.Manager;

@Controller
public class ManagerProjectMemberController extends AbstractController<Manager, ProjectMember> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		// super.addBasicCommand("list", ManagerNewMemberListService.class);
		// super.addBasicCommand("show", ManagerNewMemberListService.class);
		super.addBasicCommand("create", ManagerProjectMemberCreateService.class);
		//super.addBasicCommand("update", ManagerProjectUpdateService.class);
		super.addBasicCommand("delete", ManagerProjectMemberDeleteService.class);

		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
