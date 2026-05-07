
package acme.features.member.invention.inventor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.inventions.Invention;
import acme.features.member.invention.MemberInventionRepository;
import acme.realms.Inventor;
import acme.realms.Member;

@Service
public class MemberInventionInventorShowService extends AbstractService<Member, Inventor> {

	@Autowired
	private MemberInventionInventorRepository	repository;

	@Autowired
	private MemberInventionRepository			inventionRepository;

	private Inventor							inventor;
	private Invention							invention;


	@Override
	public void load() {
		int id;
		int memberId;

		id = super.getRequest().getData("id", int.class);
		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		this.inventor = this.repository.findInventorById(id);

		this.invention = null;

		if (this.inventor != null) {
			var inventions = this.inventionRepository.findInventionsByInventorId(id);

			for (Invention c : inventions)
				if (c.getProject() != null) {
					Integer count = this.inventionRepository.checkProjectBelongsToMember(c.getProject().getId(), memberId);

					if (count != null && count > 0) {
						this.invention = c;
						break;
					}
				}
		}
	}

	@Override
	public void authorise() {
		boolean status = false;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		if (this.invention != null && this.invention.getProject() != null) {
			int projectId = this.invention.getProject().getId();

			Integer count = this.inventionRepository.checkProjectBelongsToMember(projectId, memberId);

			status = count != null && count > 0;
		}

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObject(this.inventor, "bio", "keyWords", "licensed", "userAccount.identity.fullName", "userAccount.identity.email");

		super.unbindGlobal("draftMode", this.invention.getDraftMode());
		super.unbindGlobal("inventionId", this.invention.getId());
		super.unbindGlobal("id", this.inventor.getId());
	}
}
