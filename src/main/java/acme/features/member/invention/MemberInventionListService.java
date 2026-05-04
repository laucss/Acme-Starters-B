
package acme.features.member.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Member;

@Service
public class MemberInventionListService extends AbstractService<Member, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberInventionRepository	repository;

	private Collection<Invention>		inventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.inventions = this.repository.findInventionsByProjectId(projectId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "name", "inventor.userAccount.identity.fullName", "cost", "monthsActive", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
	}

}
