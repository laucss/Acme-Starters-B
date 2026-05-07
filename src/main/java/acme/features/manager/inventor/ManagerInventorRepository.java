
package acme.features.manager.inventor;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Inventor;

@Repository
public interface ManagerInventorRepository extends AbstractRepository {

	@Query("select r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

	@Query("select i from ProjectMember pm join pm.member m join Inventor i on i.userAccount = m.userAccount where pm.project.id = :projectId and pm.role = :role")
	Collection<Inventor> findInventorMembersByProjectId(int projectId, MemberRole role);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select i from Inventor i where i.id = :inventorId")
	Inventor findInventorById(int inventorId);

}
