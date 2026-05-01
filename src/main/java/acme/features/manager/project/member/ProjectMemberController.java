
package acme.features.manager.project.member;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectMember;
import acme.realms.Manager;

@Controller
public class ProjectMemberController extends AbstractController<Manager, ProjectMember> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", ProjectMemberListService.class);
		// super.addBasicCommand("show", ManagerProjectShowService.class);
		//super.addBasicCommand("create", ManagerProjectCreateService.class);
		//super.addBasicCommand("update", ManagerProjectUpdateService.class);
		//super.addBasicCommand("delete", ManagerProjectDeleteService.class);

		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
