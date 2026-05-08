
package acme.features.member.invention;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.features.member.project.MemberProjectRepository;
import acme.realms.Inventor;
import acme.realms.Member;

@Service
public class MemberInventionListService extends AbstractService<Member, Invention> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberInventionRepository	repository;

	private Collection<Invention>		inventions;

	private Project						project;

	@Autowired
	private MemberProjectRepository		projectRepository;

	// AbstractService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null) {

			boolean isPublished = !this.project.getDraftMode();

			Integer count = this.repository.checkProjectBelongsToMember(this.project.getId(), memberId);
			boolean isMember = count != null && count > 0;

			status = isPublished || isMember;
		}

		super.setAuthorised(status);
	}

	@Override
	public void load() {
		int projectId;

		projectId = super.getRequest().getData("projectId", int.class);
		this.inventions = this.repository.findInventionsByProjectId(projectId);
		this.project = this.projectRepository.findProjectById(projectId);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.inventions, //
			"ticker", "name", "inventor.userAccount.identity.fullName", "cost", "monthsActive", "description", "startMoment", "endMoment", "moreInfo", "draftMode");
		super.unbindGlobal("projectId", this.project.getId());
		super.unbindGlobal("draftMode", this.project.getDraftMode());
		boolean isInventor = super.getRequest().getPrincipal().getRealms().stream().anyMatch(Inventor.class::isInstance);
		if (super.getRequest().hasData("projectId")) {
			super.unbindGlobal("isInventor", isInventor);
			super.unbindGlobal("projectId", this.project.getId());
		}
	}

}
