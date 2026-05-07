
package acme.features.member.campaign.spokesperson;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.features.member.campaign.MemberCampaignRepository;
import acme.realms.Member;
import acme.realms.Spokesperson;

public class MemberCampaignSpokespersonShowService extends AbstractService<Member, Spokesperson> {

	@Autowired
	private MemberCampaignSpokespersonRepository	repository;

	@Autowired
	private MemberCampaignRepository				campaignRepository;

	private Spokesperson							spokesperson;
	private Campaign								campaign;


	@Override
	public void load() {
		int id;
		int memberId;

		id = super.getRequest().getData("id", int.class);
		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		this.spokesperson = this.repository.findSpokespersonById(id);

		this.campaign = null;

		if (this.spokesperson != null) {
			var campaigns = this.campaignRepository.findCampaignsBySpokespersonId(id);

			for (Campaign c : campaigns)
				if (c.getProject() != null) {
					Integer count = this.campaignRepository.checkProjectBelongsToMember(c.getProject().getId(), memberId);

					if (count != null && count > 0) {
						this.campaign = c;
						break;
					}
				}
		}
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.campaign != null && this.campaign.getProject() != null) {
			int projectId = this.campaign.getProject().getId();

			Integer count = this.campaignRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.spokesperson, "cv", "achievements", "licensed", "userAccount.identity.fullName", "userAccount.identity.email");

		super.unbindGlobal("draftMode", this.campaign.getDraftMode());
		super.unbindGlobal("campaignId", this.campaign.getId());
		super.unbindGlobal("id", this.spokesperson.getId());
	}
}
