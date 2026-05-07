
package acme.features.any.strategy;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.strategies.Strategy;

@Service
public class AnyStrategyListService extends AbstractService<Any, Strategy> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyStrategyRepository	repository;

	private Collection<Strategy>	strategies;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Integer projectId = null;

		if (super.getRequest().hasData("projectId"))
			projectId = super.getRequest().getData("projectId", Integer.class);

		if (projectId != null)
			this.strategies = this.repository.findStrategiesByProjectId(projectId);
		else
			this.strategies = this.repository.findPublishedStrategies();
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
		super.unbindObjects(this.strategies, //
			"ticker", "name", "fundraiser.userAccount.identity.name", "expectedPercentage");
	}

}
