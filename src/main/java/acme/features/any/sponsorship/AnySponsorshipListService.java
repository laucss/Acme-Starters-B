
package acme.features.any.sponsorship;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class AnySponsorshipListService extends AbstractService<Any, Sponsorship> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnySponsorshipRepository	repository;

	private Collection<Sponsorship>		sponsorships;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Integer projectId = null;

		if (super.getRequest().hasData("projectId"))
			projectId = super.getRequest().getData("projectId", Integer.class);

		if (projectId != null)
			this.sponsorships = this.repository.findSponsorshipsByProjectId(projectId);
		else
			this.sponsorships = this.repository.findPublishedSponsorships();

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
		Integer projectId = super.getRequest().hasData("projectId") ? super.getRequest().getData("projectId", Integer.class) : null;
		boolean isSponsor = super.getRequest().getPrincipal().hasRealmOfType(Sponsor.class);
		super.getResponse().addGlobal("projectId", projectId);
		super.getResponse().addGlobal("isSponsor", isSponsor);
		super.unbindObjects(this.sponsorships, //
			"ticker", "name", "sponsor.userAccount.identity.fullName", "totalMoney");
	}
}
