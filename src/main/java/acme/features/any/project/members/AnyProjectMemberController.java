
package acme.features.any.project.members;

import javax.annotation.PostConstruct;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractController;
import acme.entities.projects.ProjectMember;

@Controller
public class AnyProjectMemberController extends AbstractController<Any, ProjectMember> {

	@PostConstruct
	protected void initialise() {
		super.setMediaType(MediaType.TEXT_HTML);

		super.addBasicCommand("list", AnyProjectMemberListService.class);
		super.addBasicCommand("show", AnyProjectMemberShowService.class);
		//super.addBasicCommand("create", ManagerProjectCreateService.class);
		//super.addBasicCommand("update", ManagerProjectUpdateService.class);
		//super.addBasicCommand("delete", ManagerProjectDeleteService.class);

		//super.addCustomCommand("publish", "update", FundraiserStrategyPublishService.class);
	}

}
