
package acme.features.manager.projectMember;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Manager;

@Service
public class ManagerNewMemberListService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerNewMemberRepository	repository;

	private Collection<ProjectMember>	projectMembers;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		String roleUrl;
		MemberRole role;

		projectId = super.getRequest().getData("projectId", int.class);
		roleUrl = super.getRequest().getData("role", String.class);

		if ("FUNDRAISER".equals(roleUrl))
			role = MemberRole.FUNDRAISER;
		else
			role = MemberRole.INVENTOR;

		this.project = this.repository.findProjectById(projectId);
		this.projectMembers = this.repository.findProjectMembersByProjectIdAndRole(projectId, role);

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.project != null && // 
			(this.project.getManager().isPrincipal() || //
				!this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.unbindObjects(this.projectMembers, //
			"member.userAccount.username", "member.userAccount.identity.email");

		super.unbindGlobal("projectId", this.project.getId());

	}

}
