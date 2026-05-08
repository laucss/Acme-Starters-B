
package acme.features.member.invention;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.realms.Member;

@Service
public class MemberInventionShowService extends AbstractService<Member, Invention> {

	@Autowired
	private MemberInventionRepository	repository;
	private Invention					invention;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.invention = this.repository.findInventionById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.invention != null) {

			boolean isPublish = !this.invention.getProject().getDraftMode();

			int projectId = this.invention.getProject().getId();

			Integer count = this.repository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.invention, "ticker", "name", "description", "startMoment", "endMoment", "monthsActive", "cost", "moreInfo", "draftMode");
		super.unbindGlobal("draftMode", this.invention.getDraftMode());
		super.unbindGlobal("id", this.invention.getId());
		super.unbindGlobal("inventorId", this.invention.getInventor().getId());
		if (this.invention.getProject() != null && this.invention.getInventor().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId())
			super.unbindGlobal("projectId", this.invention.getProject().getId());
	}
}
