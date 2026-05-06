
package acme.features.any.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;

@Service
public class AnyInventionListService extends AbstractService<Any, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyInventionRepository	repository;

	private Collection<Invention>	inventions;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Integer projectId = null;

		if (super.getRequest().hasData("projectId"))
			projectId = super.getRequest().getData("projectId", Integer.class);

		if (projectId != null)
			this.inventions = this.repository.findInventionsByProjectId(projectId);
		else
			this.inventions = this.repository.findPublishedInventions();
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
		super.unbindObjects(this.inventions, //
			"ticker", "name", "inventor.userAccount.identity.fullName", "cost", "monthsActive", "description", "startMoment", "endMoment", "moreInfo");
	}

}
