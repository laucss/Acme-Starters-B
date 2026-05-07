
package acme.features.manager.projectMember;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.entities.strategies.Strategy;
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
	Member findMemberByUserAccountId(int userAccountId);

	@Query("select p from ProjectMember p where p.project.id = :projectId and p.id = :pmId")
	ProjectMember findProjectMemberByIdAndProjectId(int projectId, int pmId);

	@Query("select p from ProjectMember p where p.member.id = :memberId")
	List<ProjectMember> findProjectMemberByMemberId(int memberId);

	@Query("select m from Member m where m.id = :memberId")
	Member findMemberById(int memberId);

	@Query("select f from Fundraiser f where f.userAccount.id = :id")
	Fundraiser findFundraiserByUserAccountId(int id);

	@Query("select f from Inventor f where f.userAccount.id = :id")
	Inventor findInventorByUserAccountId(int id);

	@Query("select f from Spokesperson f where f.userAccount.id = :id")
	Spokesperson findSpokespersonByUserAccountId(int id);

	@Query("select s from Strategy s where s.fundraiser.id = :fundraiserId and s.project.id =:projectId")
	List<Strategy> findStrategiesByFundraiserIdAndProjectId(int fundraiserId, int projectId);

	@Query("select i from Invention i where i.inventor.id = :inventorId and i.project.id =:projectId")
	List<Invention> findInventionsByInventorIdAndProjectId(int inventorId, int projectId);

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId and c.project.id =:projectId")
	List<Campaign> findCampaignBySpokespersonIdAndProjectId(int spokespersonId, int projectId);

}
