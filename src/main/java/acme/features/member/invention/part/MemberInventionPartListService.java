
package acme.features.member.invention.part;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.entities.projects.Project;
import acme.features.member.invention.MemberInventionRepository;
import acme.features.member.project.MemberProjectRepository;
import acme.realms.Member;

@Service
public class MemberInventionPartListService extends AbstractService<Member, Part> {

	@Autowired
	private MemberInventionPartRepository	repository;
	@Autowired
	private MemberInventionRepository		inventionRepository;
	@Autowired
	private MemberProjectRepository			projectRepository;

	private Collection<Part>				part;
	private Invention						invention;
	private Project							project;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("inventionId", int.class);

		this.part = this.repository.findAllPartByInventionId(id);
		this.invention = this.inventionRepository.findInventionById(id);

		if (this.invention != null)
			this.project = this.projectRepository.findProjectById(this.invention.getProject().getId());
		else
			this.project = null;
	}

	@Override
	public void authorise() {
		boolean status = false;

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.project != null || this.invention != null) {

			boolean isPublish = !this.project.getDraftMode();
			Integer count = this.inventionRepository.checkProjectBelongsToMember(this.project.getId(), memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.part, "name", "description", "cost", "kind");
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("draftMode", this.invention.getDraftMode());
	}
}
