
package acme.features.member.invention.part;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;
import acme.entities.inventions.PartKind;
import acme.features.member.invention.MemberInventionRepository;
import acme.realms.Member;

@Service
public class MemberInventionPartShowService extends AbstractService<Member, Part> {

	//Internal state
	@Autowired
	private MemberInventionPartRepository	repository;
	@Autowired
	private MemberInventionRepository		inventionRepository;

	private Part							part;
	private Invention						invention;


	//AbstractService interface
	@Override
	public void load() {
		int id;
		id = super.getRequest().getData("id", int.class);
		this.part = this.repository.findPartById(id);

		if (this.part != null)
			this.invention = this.part.getInvention();
		else
			this.invention = null;
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.invention != null) {

			boolean isPublish = !this.invention.getProject().getDraftMode();

			int projectId = this.invention.getProject().getId();

			Integer count = this.inventionRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0 || isPublish;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.part, "name", "description", "cost", "kind");
		super.unbindGlobal("draftMode", this.part.getInvention().getDraftMode());
		super.unbindGlobal("id", this.part.getId());
		SelectChoices opcionesKind = SelectChoices.from(PartKind.class, this.part.getKind());
		super.unbindGlobal("kinds", opcionesKind);
	}
}
