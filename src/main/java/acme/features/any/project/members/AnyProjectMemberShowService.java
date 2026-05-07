
package acme.features.any.project.members;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Tuple;
import acme.client.components.principals.Any;
import acme.client.services.AbstractService;
import acme.entities.projects.MemberRole;
import acme.entities.projects.ProjectMember;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Spokesperson;

@Service
public class AnyProjectMemberShowService extends AbstractService<Any, ProjectMember> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyProjectMemberRepository	repository;

	private ProjectMember				projectMember;

	// AbstractService interface -------------------------------------------


	@Override
	public void load() {
		int id;

		id = super.getRequest().getData("id", int.class);
		this.projectMember = this.repository.findProjectMemberById(id);

	}

	@Override
	public void authorise() {
		boolean status;

		status = this.projectMember != null && // 
			(this.projectMember.getProject().getManager().isPrincipal() || //
				!this.projectMember.getProject().getDraftMode());

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {

		Tuple tuple;

		tuple = super.unbindObject(this.projectMember, //
			"member.userAccount.username", "member.userAccount.identity.name", "member.userAccount.identity.surname", "member.userAccount.identity.email", "role");

		MemberRole rol = this.projectMember.getRole();
		int userAccountId = this.projectMember.getMember().getUserAccount().getId();

		if (rol.equals(MemberRole.FUNDRAISER)) {
			Fundraiser fundraiser = this.repository.findFundraiserByUserAccountId(userAccountId);
			tuple.put("bank", fundraiser.getBank());
			tuple.put("statement", fundraiser.getStatement());
			tuple.put("agent", fundraiser.getAgent());
			//
		} else if (rol.equals(MemberRole.INVENTOR)) {
			Inventor inventor = this.repository.findInventorByUserAccountId(userAccountId);
			tuple.put("bio", inventor.getBio());
			tuple.put("keyWords", inventor.getKeyWords());
			tuple.put("licensed", inventor.getLicensed());
			//
		} else if (rol.equals(MemberRole.SPOKESPERSON)) {
			Spokesperson spokesperson = this.repository.findSpokespersonByUserAccountId(userAccountId);
			tuple.put("cv", spokesperson.getCv());
			tuple.put("achievements", spokesperson.getAchievements());
			tuple.put("licensed", spokesperson.getLicensed());
		}

		super.unbindGlobal("rol", rol);

	}

}
