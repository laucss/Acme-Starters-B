
package acme.features.member.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;
import acme.features.member.project.MemberProjectRepository;
import acme.realms.Member;
import acme.realms.Spokesperson;

@Service
public class MemberCampaignListService extends AbstractService<Member, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberCampaignRepository	repository;

	private Collection<Campaign>		campaigns;

	@Autowired
	private MemberProjectRepository		projectRepository;

	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.campaigns = this.repository.findCamapignsByProjectId(projectId);
		this.project = this.projectRepository.findProjectById(projectId);
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null) {

			boolean isPublished = !this.project.getDraftMode();

			Integer count = this.repository.checkProjectBelongsToMember(this.project.getId(), memberId);
			boolean isMember = count != null && count > 0;

			status = isPublished || isMember;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, //
			"ticker", "name", "draftMode", "effort");
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		boolean isSpokesperson = super.getRequest().getPrincipal().hasRealmOfType(Spokesperson.class);
		;
		if (super.getRequest().hasData("projectId")) {
			super.unbindGlobal("isSpokesperson", isSpokesperson);
			super.unbindGlobal("projectId", this.project.getId());
		}
	}

}
