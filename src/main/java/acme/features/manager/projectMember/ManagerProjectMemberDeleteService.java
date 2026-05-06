
package acme.features.manager.projectMember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Manager;

@Service
public class ManagerProjectMemberDeleteService extends AbstractService<Manager, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectMemberRepository	repository;

	private Project							project;
	private ProjectMember					projectMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);

		this.projectMember = super.newObject(ProjectMember.class);
		this.projectMember.setProject(this.project);
	}

	@Override
	public void authorise() {
		String roleUrl = super.getRequest().getData("role", String.class);

		boolean validRole = "SPOKESPERSON".equals(roleUrl) || "FUNDRAISER".equals(roleUrl) || "INVENTOR".equals(roleUrl);
		Boolean status = this.project != null && this.project.getManager().getId() == super.getRequest().getPrincipal().getActiveRealm().getId() && validRole;
		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.projectMember);

		int selectedPMId = super.getRequest().getData("selected", int.class);

		String roleUrl = super.getRequest().getData("role", String.class);

		ProjectMember pmSelected = this.repository.findProjectMemberByIdAndProjectId(this.project.getId(), selectedPMId);

		if (pmSelected != null)
			this.projectMember = pmSelected;

	}

	@Override
	public void validate() {
		super.validateObject(this.projectMember);

		//if (this.projectMember.getMember() == null)
		//super.state(false, "selected", "projectMember.create.validation.NoUserSelected");
	}

	@Override
	public void execute() {
		this.repository.delete(this.projectMember);
	}

	@Override
	public void unbind() {
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);

		super.unbindObject(this.projectMember);

		SelectChoices choices = null;

		String roleUrl = super.getRequest().getData("role", String.class);

		if ("FUNDRAISER".equals(roleUrl)) {
			List<ProjectMember> fundraiserAsignados = this.repository.findProjectMembersByProjectIdAndRole(this.project.getId(), MemberRole.FUNDRAISER);
			choices = SelectChoices.from(fundraiserAsignados, "member.userAccount.username", null);
			//
		} else if ("INVENTOR".equals(roleUrl)) {
			List<ProjectMember> inventorsAsignados = this.repository.findProjectMembersByProjectIdAndRole(this.project.getId(), MemberRole.INVENTOR);
			choices = SelectChoices.from(inventorsAsignados, "member.userAccount.username", null);
			//
		} else if ("SPOKESPERSON".equals(roleUrl)) {
			List<ProjectMember> spokespeopleAsignados = this.repository.findProjectMembersByProjectIdAndRole(this.project.getId(), MemberRole.SPOKESPERSON);
			choices = SelectChoices.from(spokespeopleAsignados, "member.userAccount.username", null);
		}

		super.unbindGlobal("choices", choices);
		super.unbindGlobal("projectId", projectId);
		super.unbindGlobal("role", roleUrl);

	}

}
