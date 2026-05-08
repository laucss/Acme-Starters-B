
package acme.features.member.strategy;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Member;

public class MemberStrategyShowService extends AbstractService<Member, Strategy> {

	@Autowired
	private MemberStrategyRepository	repository;
	private Strategy					strategy;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.strategy = this.repository.findStrategyById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.strategy != null) {

			boolean isPublish = !this.strategy.getProject().getDraftMode();

			int projectId = this.strategy.getProject().getId();

			Integer count = this.repository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.strategy, "ticker", "name", "description", "startMoment", "endMoment", "moreInfo", "monthsActive", "expectedPercentage", "draftMode");
		super.unbindGlobal("id", this.strategy.getId());
		super.unbindGlobal("fundraiserId", this.strategy.getFundraiser().getId());
		if (this.strategy.getProject() != null && this.strategy.getFundraiser().getUserAccount().getId() == super.getRequest().getPrincipal().getAccountId())
			super.unbindGlobal("projectId", this.strategy.getProject().getId());
	}
}
