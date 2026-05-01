
package acme.features.manager.project.member;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Manager;

@Service
public class ProjectMemberListService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ProjectMemberRepository		repository;

	private Collection<ProjectMember>	projectMembers;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		this.projectMembers = this.repository.findProjectMembersByProjectId(projectId);

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

		boolean showAdd;

		super.unbindObjects(this.projectMembers, //
			"member", "member.username");

		showAdd = this.project.getDraftMode() && this.project.getManager().isPrincipal();
		super.unbindGlobal("showAdd", showAdd);
		super.unbindGlobal("projectId", this.project.getId());

	}

}
