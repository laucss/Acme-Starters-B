
package acme.features.any.campaign;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.campaigns.Campaign;

@Service
public class AnyCampaignListService extends AbstractService<Any, Campaign> {

	@Autowired
	private AnyCampaignRepository	repository;

	private Collection<Campaign>	campaigns;


	@Override
	public void load() {
		Integer projectId = null;

		if (super.getRequest().hasData("projectId"))
			projectId = super.getRequest().getData("projectId", Integer.class);

		if (projectId != null)
			this.campaigns = this.repository.findCampaignsByProjectId(projectId);
		else
			this.campaigns = this.repository.findPublishedCampaigns();
	}

	@Override
	public void authorise() {
		boolean status = true;

		if (super.getRequest().hasData("projectId")) {
			int projectId = super.getRequest().getData("projectId", int.class);

			var project = this.repository.findProjectById(projectId);

			status = project != null && !project.getDraftMode();
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		super.getResponse().addData(super.unbindObjects(this.campaigns, "ticker", "name", "monthsActive", "effort"));
	}

}
