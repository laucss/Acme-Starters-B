
package acme.features.member.campaign.milestone;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.projects.Project;
import acme.features.member.campaign.MemberCampaignRepository;
import acme.features.member.project.MemberProjectRepository;
import acme.realms.Member;

public class MemberCampaignMilestoneListService extends AbstractService<Member, Milestone> {

	@Autowired
	private MemberCampaignMilestoneRepository	repository;
	@Autowired
	private MemberCampaignRepository			campaignRepository;
	@Autowired
	private MemberProjectRepository				projectRepository;

	private Collection<Milestone>				milestone;
	private Campaign							campaign;
	private Project								project;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("campaignId", int.class);

		this.milestone = this.repository.findAllMilestoneByCampaignId(id);
		this.campaign = this.campaignRepository.findCampaignById(id);

		if (this.campaign != null)
			this.project = this.projectRepository.findProjectById(this.campaign.getProject().getId());
		else
			this.project = null;
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null || this.campaign != null) {
			Integer count = this.campaignRepository.checkProjectBelongsToMember(this.project.getId(), memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Campaign c;
		int id;
		id = super.getRequest().getData("campaignId", int.class);
		c = this.campaignRepository.findCampaignById(id);
		super.unbindObjects(this.milestone, "title", "effort", "kind");
		super.unbindGlobal("draftMode", c.getDraftMode());
		super.unbindGlobal("campaignId", c.getId());
	}
}
