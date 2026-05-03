
package acme.features.inventor.project;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Tuple;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Inventor;

public class InventorProjectShowService extends AbstractService<Inventor, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private InventorProjectRepository	repository;

	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int userAccountId = super.getRequest().getPrincipal().getAccountId();

		status = this.project != null && // 
			(this.repository.isMember(this.project.getId(), userAccountId) || //
				!this.project.getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		Tuple tuple = super.unbindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut", "personMonths", "draftMode");
		tuple.put("managerId", this.project.getManager().getId());
		tuple.put("id", this.project.getId());
	}

}
