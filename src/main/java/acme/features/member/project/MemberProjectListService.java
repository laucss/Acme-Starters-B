
package acme.features.member.project;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.services.AbstractService;
import acme.entities.projects.Project;
import acme.realms.Member;

@Service
public class MemberProjectListService extends AbstractService<Member, Project> {

	@Autowired
	private MemberProjectRepository	repository;
	private Collection<Project>		projects;


	@Override
	public void load() {
		int id;
		id = super.getRequest().getPrincipal().getActiveRealm().getId();
		this.projects = this.repository.findProjectsByMemberId(id);
	}

	@Override
	public void authorise() {
		boolean status;
		int memberId;

		memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		status = this.projects.stream().allMatch(p -> this.repository.isMember(p.getId(), memberId) > 0);

		super.setAuthorised(status);
	}

	@Override
	public void unbind() {
		super.unbindObjects(this.projects, "title", "keyWords", "description", "kickOff", "closeOut");
	}

}
