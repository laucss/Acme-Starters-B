
package acme.features.member.strategy.tactic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.entities.strategies.TacticKind;
import acme.features.member.strategy.MemberStrategyRepository;
import acme.realms.Member;

@Service
public class MemberStrategyTacticShowService extends AbstractService<Member, Tactic> {

	//Internal state
	@Autowired
	private MemberStrategyTacticRepository	repository;
	@Autowired
	private MemberStrategyRepository		strategyRepository;

	private Tactic							tactic;
	private Strategy						strategy;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.tactic = this.repository.findTacticById(id);

		if (this.tactic != null)
			this.strategy = this.tactic.getStrategy();
		else
			this.strategy = null;
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.strategy != null) {

			int projectId = this.strategy.getProject().getId();

			Integer count = this.strategyRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.tactic, "name", "expectedPercentage", "kind", "notes");
		super.unbindGlobal("draftMode", this.tactic.getStrategy().getDraftMode());
		super.unbindGlobal("strategyId", this.tactic.getStrategy().getId());
		super.unbindGlobal("id", this.tactic.getId());
		SelectChoices opcionesKind = SelectChoices.from(TacticKind.class, this.tactic.getKind());
		super.unbindGlobal("kinds", opcionesKind);
	}
}
