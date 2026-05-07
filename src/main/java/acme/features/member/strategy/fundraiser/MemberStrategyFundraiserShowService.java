
package acme.features.member.strategy.fundraiser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.features.member.strategy.MemberStrategyRepository;
import acme.realms.Fundraiser;
import acme.realms.Member;

@Service
public class MemberStrategyFundraiserShowService extends AbstractService<Member, Fundraiser> {

	@Autowired
	private MemberStrategyFundraiserRepository	repository;

	@Autowired
	private MemberStrategyRepository			strategyRepository;

	private Fundraiser							fundraiser;
	private Strategy							strategy;


	@Override
	public void load() {
		int id;
		int memberId;

		id = super.getRequest().getData("id", int.class);
		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		this.fundraiser = this.repository.findFundraiserById(id);

		this.strategy = null;

		if (this.fundraiser != null) {
			var strategies = this.strategyRepository.findStrategiesByFundraiserId(id);

			for (Strategy c : strategies)
				if (c.getProject() != null) {
					Integer count = this.strategyRepository.checkProjectBelongsToMember(c.getProject().getId(), memberId);

					if (count != null && count > 0) {
						this.strategy = c;
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

		if (this.strategy != null && this.strategy.getProject() != null) {
			int projectId = this.strategy.getProject().getId();

			Integer count = this.strategyRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.fundraiser, "bank", "statement", "agent", "userAccount.identity.fullName", "userAccount.identity.email");

		super.unbindGlobal("draftMode", this.strategy.getDraftMode());
		super.unbindGlobal("strategyId", this.strategy.getId());
		super.unbindGlobal("id", this.fundraiser.getId());
	}
}
