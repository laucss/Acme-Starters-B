
package acme.features.manager.spokesperson;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.components.principals.UserAccount;
import acme.client.repositories.AbstractRepository;
import acme.entities.projects.MemberRole;
import acme.entities.projects.Project;
import acme.realms.Spokesperson;

@Repository
public interface ManagerSpokespersonRepository extends AbstractRepository {

	@Query("select r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

	@Query("select s from ProjectMember pm join pm.member m join Spokesperson s on s.userAccount = m.userAccount where pm.project.id = :projectId and pm.role = :role")
	Collection<Spokesperson> findSpokespersonMembersByProjectId(int projectId, MemberRole role);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select s from Spokesperson s where s.id = :spokespersonId")
	Spokesperson findSpokespersonById(int spokespersonId);

}
