
package acme.features.fundraiser.strategy_assignment;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.features.member.project.MemberProjectRepository;
import acme.forms.StrategyAssignment;
import acme.realms.Fundraiser;

@Service
public class FundraiserStrategyAssignmentCreateService extends AbstractService<Fundraiser, StrategyAssignment> {

	@Autowired
	private FundraiserStrategyAssignmentRepository	repository;

	@Autowired
	private MemberProjectRepository					projectRepository;

	private StrategyAssignment						strategyAssigment;
	private Collection<Strategy>					strategies;
	private Project									project;


	@Override
	public void load() {
		int projectId = super.getRequest().getData("projectId", int.class);
		this.project = this.repository.findProjectById(projectId);
		int fundraiserId = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.strategies = this.repository.findAvailableStrategyByFundraiserId(fundraiserId);
		this.strategyAssigment = super.newObject(StrategyAssignment.class);
		this.strategyAssigment.setProjectId(projectId);

	}

	@Override
	public void authorise() {
		boolean status = false;

		int accountId = super.getRequest().getPrincipal().getAccountId();

		if (this.project != null) {

			Integer count = this.projectRepository.checkProjectBelongsToAccountId(this.project.getId(), accountId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		int projectId = super.getRequest().getData("projectId", int.class);
		super.bindObject(this.strategyAssigment, "strategyId");
		this.strategyAssigment.setProjectId(projectId);
	}

	@Override
	public void validate() {
		super.validateObject(this.strategyAssigment);
	}

	@Override
	public void execute() {
		Strategy strategy = this.repository.findStrategyById(this.strategyAssigment.getStrategyId());
		if (strategy != null) {
			strategy.setProject(this.project);
			this.repository.save(strategy);
		}
	}

	@Override
	public void unbind() {
		SelectChoices choices;
		choices = SelectChoices.from(this.strategies, "ticker", null);
		super.unbindObject(this.strategyAssigment, "strategyId");
		super.unbindGlobal("listaStrategies", choices);
		super.unbindGlobal("projectId", this.project.getId());
	}
}
