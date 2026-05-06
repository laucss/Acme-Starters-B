
package acme.features.manager.projectMember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.UserAccount;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Manager;
import acme.realms.Member;
import acme.realms.Spokesperson;

@Service
public class ManagerProjectMemberCreateService extends AbstractService<Manager, ProjectMember> {

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

		int selectedUserId = super.getRequest().getData("selected", int.class);

		String roleUrl = super.getRequest().getData("role", String.class);

		MemberRole role = null;

		UserAccount cuenta = null;

		if ("FUNDRAISER".equals(roleUrl)) {
			Fundraiser fundraiser = this.repository.findFundraiserById(selectedUserId);
			cuenta = fundraiser.getUserAccount();
			role = MemberRole.FUNDRAISER;
			//
		} else if ("INVENTOR".equals(roleUrl)) {
			Inventor inventor = this.repository.findInventorById(selectedUserId);
			cuenta = inventor.getUserAccount();
			role = MemberRole.INVENTOR;
			//
		} else if ("SPOKESPERSON".equals(roleUrl)) {
			Spokesperson spokesperson = this.repository.findSpokespersonById(selectedUserId);
			cuenta = spokesperson.getUserAccount();
			role = MemberRole.SPOKESPERSON;
		}

		if (cuenta != null) {
			this.projectMember.setRole(role);
			Member member = this.repository.findMemberByUserAccountId(cuenta.getId());
			if (member == null) {
				member = super.newObject(Member.class);
				member.setUserAccount(cuenta);
				this.repository.save(member);
			}
			this.projectMember.setMember(member);
		}

	}

	@Override
	public void validate() {
		super.validateObject(this.projectMember);

		if (this.projectMember.getMember() == null)
			super.state(false, "selected", "projectMember.create.validation.NoUserSelected");
	}

	@Override
	public void execute() {
		this.repository.save(this.projectMember);
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
			List<Fundraiser> fundraiserNoAsignados = this.repository.findFundraisersNotInProject(this.project.getId());
			choices = SelectChoices.from(fundraiserNoAsignados, "userAccount.username", null);
			//
		} else if ("INVENTOR".equals(roleUrl)) {
			List<Inventor> inventorsNoAsignados = this.repository.findInventorsNotInProject(this.project.getId());
			choices = SelectChoices.from(inventorsNoAsignados, "userAccount.username", null);
			//
		} else if ("SPOKESPERSON".equals(roleUrl)) {
			List<Spokesperson> spokespeopleNoAsignados = this.repository.findSpokespersonsNotInProject(this.project.getId());
			choices = SelectChoices.from(spokespeopleNoAsignados, "userAccount.username", null);
		}

		super.unbindGlobal("choices", choices);
		super.unbindGlobal("projectId", projectId);
		super.unbindGlobal("role", roleUrl);

	}

}
