
package acme.features.member.campaign.milestone;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.entities.campaigns.Milestone;
import acme.entities.campaigns.MilestoneKind;
import acme.features.member.campaign.MemberCampaignRepository;
import acme.realms.Member;

public class MemberCampaignMilestoneShowService extends AbstractService<Member, Milestone> {

	//Internal state
	@Autowired
	private MemberCampaignMilestoneRepository	repository;
	@Autowired
	private MemberCampaignRepository			campaignRepository;

	private Milestone							milestone;
	private Campaign							campaign;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.milestone = this.repository.findMilestoneById(id);

		if (this.milestone != null)
			this.campaign = this.milestone.getCampaign();
		else
			this.campaign = null;
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.campaign != null) {

			int projectId = this.campaign.getProject().getId();

			Integer count = this.campaignRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.milestone, "title", "achievements", "effort", "kind");
		super.unbindGlobal("draftMode", this.milestone.getCampaign().getDraftMode());
		super.unbindGlobal("campaignId", this.milestone.getCampaign().getId());
		super.unbindGlobal("id", this.milestone.getId());
		SelectChoices opcionesKind = SelectChoices.from(MilestoneKind.class, this.milestone.getKind());
		super.unbindGlobal("kinds", opcionesKind);
	}
}
