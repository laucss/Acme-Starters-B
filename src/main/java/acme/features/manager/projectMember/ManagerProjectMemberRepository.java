
package acme.features.manager.projectMember;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Member;
import acme.realms.Spokesperson;

@Repository
public interface ManagerProjectMemberRepository extends AbstractRepository {

	@Query("select r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

	@Query("select pm from ProjectMember pm where pm.project.id = :projectId and pm.role = :role")
	List<ProjectMember> findProjectMembersByProjectIdAndRole(int projectId, MemberRole role);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select f from Fundraiser f where not exists (select pm from ProjectMember pm where pm.project.id = :projectId and pm.role = acme.entities.projects.MemberRole.FUNDRAISER and pm.member.userAccount = f.userAccount)")
	List<Fundraiser> findFundraisersNotInProject(int projectId);

	@Query("select i from Inventor i where not exists (select pm from ProjectMember pm where pm.project.id = :projectId and pm.role = acme.entities.projects.MemberRole.INVENTOR and pm.member.userAccount = i.userAccount)")
	List<Inventor> findInventorsNotInProject(int projectId);

	@Query("select s from Spokesperson s where not exists (select pm from ProjectMember pm where pm.project.id = :projectId and pm.role = acme.entities.projects.MemberRole.SPOKESPERSON and pm.member.userAccount = s.userAccount)")
	List<Spokesperson> findSpokespersonsNotInProject(int projectId);

	@Query("select f from Fundraiser f where f.id = :fundraiserId")
	Fundraiser findFundraiserById(int fundraiserId);

	@Query("select i from Inventor i where i.id = :inventorId")
	Inventor findInventorById(int inventorId);

	@Query("select s from Spokesperson s where s.id = :spokespersonId")
	Spokesperson findSpokespersonById(int spokespersonId);

	@Query("select m from Member m where m.userAccount.id = :userAccountId")
	Member findMemberById(int userAccountId);

}
