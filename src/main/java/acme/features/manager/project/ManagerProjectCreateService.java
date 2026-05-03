
package acme.features.manager.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Manager;

@Service
public class ManagerProjectCreateService extends AbstractService<Manager, Project> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerProjectRepository	repository;

	private Project						project;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		Manager manager;

		manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();

		this.project = super.newObject(Project.class);
		this.project.setDraftMode(true);
		this.project.setManager(manager);
	}

	@Override
	public void authorise() {
		super.setAuthorised(true);
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
		this.repository.save(this.project);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut", "draftMode");
	}

}
