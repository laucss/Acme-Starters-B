
package acme.features.manager.spokesperson;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Manager;
import acme.realms.Spokesperson;

@Service
public class ManagerSpokespersonListService extends AbstractService<Manager, Spokesperson> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerSpokespersonRepository	repository;

	private Collection<Spokesperson>		spokespersons;
	private Project							project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);
		this.spokespersons = this.repository.findSpokespersonMembersByProjectId(projectId, MemberRole.SPOKESPERSON);

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

		super.unbindObjects(this.spokespersons, //
			"userAccount.username", "userAccount.identity.email", "cv", "licensed");

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());

	}

}
