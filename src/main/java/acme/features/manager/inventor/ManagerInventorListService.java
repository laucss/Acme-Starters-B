
package acme.features.manager.inventor;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Inventor;
import acme.realms.Manager;

@Service
public class ManagerInventorListService extends AbstractService<Manager, Inventor> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerInventorRepository	repository;

	private Collection<Inventor>		inventors;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);
		this.inventors = this.repository.findInventorMembersByProjectId(projectId, MemberRole.INVENTOR);

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

		super.unbindObjects(this.inventors, //
			"userAccount.username", "userAccount.identity.email", "bio", "licensed");

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());

	}

}
