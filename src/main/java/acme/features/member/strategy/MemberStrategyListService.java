
package acme.features.member.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;
import acme.realms.Member;

@Service
public class MemberStrategyListService extends AbstractService<Member, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberStrategyRepository	repository;

	private Collection<Strategy>		strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.strategies = this.repository.findStrategiesByProjectId(projectId);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.strategies, //
			"ticker", "name", "draftMode", "expectedPercentage");
	}

}
