
package acme.features.sponsor.sponsorship;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.sponsorships.Sponsorship;
import acme.realms.Sponsor;

@Service
public class SponsorSponsorshipUnassignService extends AbstractService<Sponsor, Sponsorship> {

	//Internal state
	@Autowired
	private SponsorSponsorshipRepository	repository;
	private Sponsorship						sponsorship;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.sponsorship = this.repository.findSponsorshipById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int sponsorId, sponsorshipId;
		Sponsorship spsh;
		sponsorId = super.getRequest().getPrincipal().getActiveRealm().getId();
		sponsorshipId = super.getRequest().getData("id", int.class);
		spsh = this.repository.findSponsorshipById(sponsorshipId);
		status = spsh != null && spsh.getSponsor().getId() == sponsorId && MomentHelper.getCurrentMoment().before(spsh.getProjectUnassignMoment());

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.sponsorship);
	}

	@Override
	public void validate() {
		super.validateObject(this.sponsorship);
	}

	@Override
	public void execute() {
		this.sponsorship.setProject(null);
		this.sponsorship.setProjectUnassignMoment(null);
		this.repository.save(this.sponsorship);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.sponsorship, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "draftMode");

	}

}
