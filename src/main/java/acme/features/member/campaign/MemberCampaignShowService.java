
package acme.features.member.campaign;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Member;

@Service
public class MemberCampaignShowService extends AbstractService<Member, Campaign> {

	@Autowired
	private MemberCampaignRepository	repository;
	private Campaign					campaign;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.campaign = this.repository.findCampaignById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.campaign != null) {

			boolean isPublish = !this.campaign.getProject().getDraftMode();

			int projectId = this.campaign.getProject().getId();

			Integer count = this.repository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.campaign, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "effort", "moreInfo", "draftMode");
		super.unbindGlobal("id", this.campaign.getId());
		super.unbindGlobal("spokespersonId", this.campaign.getSpokesperson().getId());
		if (this.campaign.getProject() != null && this.campaign.getSpokesperson().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId())
			super.unbindGlobal("projectId", this.campaign.getProject().getId());
	}
}
