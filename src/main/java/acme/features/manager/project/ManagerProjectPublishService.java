
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectPublishService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

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

		status = this.project != null && this.project.getDraftMode() && //
			this.project.getManager().isPrincipal();

		super.setAuthorised(status);
	}

	@Override
	public void bind() {
		super.bindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut");
	}

	@Override
	public void validate() {
		boolean kickOffIsBeforecloseOut;
		if (this.project.getKickOff() != null && this.project.getCloseOut() != null) {
			kickOffIsBeforecloseOut = MomentHelper.isBefore(this.project.getKickOff(), this.project.getCloseOut());

			super.state(kickOffIsBeforecloseOut, "*", "acme.validation.invalid-project-time-interval.message");
		}
		super.validateObject(this.project);
	}

	@Override
	public void execute() {
		this.project.setDraftMode(false);
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {

		super.unbindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut", "draftMode");

	}

}
