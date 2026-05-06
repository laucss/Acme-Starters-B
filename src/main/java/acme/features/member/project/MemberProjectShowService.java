
package acme.features.member.project;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberProjectShowService extends AbstractService<Member, Project> {

	@Autowired
	private MemberProjectRepository	repository;
	private Project					project;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.project = this.repository.findProjectById(id);
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null) {
			Integer count = this.repository.isMember(this.project.getId(), memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.project, "title", "keyWords", "description", "kickOff", "closeOut", "personMonths", "draftMode");
		super.unbindGlobal("id", this.project.getId());
		super.unbindGlobal("managerId", this.project.getManager().getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());

	}

}
