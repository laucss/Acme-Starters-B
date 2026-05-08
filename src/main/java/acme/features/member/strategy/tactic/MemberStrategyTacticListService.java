
package acme.features.member.strategy.tactic;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;
import acme.features.member.project.MemberProjectRepository;
import acme.features.member.strategy.MemberStrategyRepository;
import acme.realms.Member;

@Service
public class MemberStrategyTacticListService extends AbstractService<Member, Tactic> {

	@Autowired
	private MemberStrategyTacticRepository	repository;
	@Autowired
	private MemberStrategyRepository		strategyRepository;
	@Autowired
	private MemberProjectRepository			projectRepository;

	private Collection<Tactic>				tactic;
	private Strategy						strategy;
	private Project							project;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("strategyId", int.class);

		this.tactic = this.repository.findAllTacticByStrategyId(id);
		this.strategy = this.strategyRepository.findStrategyById(id);

		if (this.strategy != null)
			this.project = this.projectRepository.findProjectById(this.strategy.getProject().getId());
		else
			this.project = null;
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null || this.strategy != null) {

			boolean isPublish = !this.project.getDraftMode();
			Integer count = this.strategyRepository.checkProjectBelongsToMember(this.project.getId(), memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Strategy s;
		int id;
		id = super.getRequest().getData("strategyId", int.class);
		s = this.strategyRepository.findStrategyById(id);
		super.unbindObjects(this.tactic, "name", "expectedPercentage", "kind");
		super.unbindGlobal("draftMode", s.getDraftMode());
		super.unbindGlobal("strategyId", s.getId());
	}
}
