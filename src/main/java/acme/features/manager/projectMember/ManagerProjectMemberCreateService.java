
package acme.features.manager.projectMember;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Manager;
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
		super.setAuthorised(true);
	}

	@Override
	public void bind() {
		//super.bindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut");
	}

	@Override
	public void validate() {
		//super.validateObject(this.project);
	}

	@Override
	public void execute() {
		//this.repository.save(this.project);
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
