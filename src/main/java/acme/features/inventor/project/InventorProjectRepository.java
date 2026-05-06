
package acme.features.inventor.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;

public interface InventorProjectRepository extends AbstractRepository {

	@Query(" select pm.project from ProjectMember pm where pm.member.userAccount.id = :userAccountId and pm.role = acme.entities.projects.MemberRole.INVENTOR")
	Collection<Project> findProjectsByInventor(int userAccountId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select count(pm) > 0 from ProjectMember pm where pm.project.id = :projectId and  pm.member.userAccount.id = :userAccountId")
	boolean isMember(int projectId, int userAccountId);

}
