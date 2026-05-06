
package acme.features.manager.fundraiser;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Fundraiser;

@Repository
public interface ManagerFundraiserRepository extends AbstractRepository {

	@Query("select r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

	@Query("select f from ProjectMember pm join pm.member m join Fundraiser f on f.userAccount = m.userAccount where pm.project.id = :projectId and pm.role = :role")
	Collection<Fundraiser> findFundraiserMembersByProjectId(int projectId, MemberRole role);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}
