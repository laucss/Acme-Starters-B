
package acme.features.any.project.members;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;
import acme.realms.Fundraiser;
import acme.realms.Inventor;
import acme.realms.Spokesperson;

@Repository
public interface AnyProjectMemberRepository extends AbstractRepository {

	@Query("select pm from ProjectMember pm where pm.project.id = :projectId")
	List<ProjectMember> findProjectMembersByProjectId(int projectId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select pm from ProjectMember pm where pm.id = :id")
	ProjectMember findProjectMemberById(int id);

	@Query("select f from Fundraiser f where f.userAccount.id = :fundraiserId")
	Fundraiser findFundraiserByUserAccountId(int fundraiserId);

	@Query("select i from Inventor i where i.userAccount.id = :inventorId")
	Inventor findInventorByUserAccountId(int inventorId);

	@Query("select s from Spokesperson s where s.userAccount.id = :spokespersonId")
	Spokesperson findSpokespersonByUserAccountId(int spokespersonId);

}
