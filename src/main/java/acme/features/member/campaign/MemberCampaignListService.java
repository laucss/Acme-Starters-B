
package acme.features.member.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;
import acme.realms.Member;

@Service
public class MemberCampaignListService extends AbstractService<Member, Campaign> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberCampaignRepository	repository;

	private Collection<Campaign>		campaigns;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.campaigns = this.repository.findCamapignsByProjectId(projectId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.campaigns, //
			"ticker", "name", "draftMode", "effort");
	}

}
