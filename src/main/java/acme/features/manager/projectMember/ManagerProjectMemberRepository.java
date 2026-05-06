
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

@Repository
public interface ManagerProjectMemberRepository extends AbstractRepository {

	@Query("select r.userAccount from AbstractRole r where type(r) in (Fundraiser, Inventor, Spokesperson)")
	Collection<UserAccount> findUsersWithRoles();

	@Query("select pm from ProjectMember pm where pm.project.id = :projectId and pm.role = :role")
	List<ProjectMember> findProjectMembersByProjectIdAndRole(int projectId, MemberRole role);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}
