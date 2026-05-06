
package acme.features.manager.fundraiser;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;
import acme.realms.Manager;

@Service
public class ManagerFundraiserListService extends AbstractService<Manager, Fundraiser> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerFundraiserRepository	repository;

	private Collection<Fundraiser>		fundraisers;
	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;
		projectId = super.getRequest().getData("projectId", int.class);

		this.project = this.repository.findProjectById(projectId);
		this.fundraisers = this.repository.findFundraiserMembersByProjectId(projectId, MemberRole.FUNDRAISER);

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

		super.unbindObjects(this.fundraisers, //
			"userAccount.username", "userAccount.identity.email", "bank", "agent");

		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());

	}

}
