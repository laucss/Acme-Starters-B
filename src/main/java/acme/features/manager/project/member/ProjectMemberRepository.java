
package acme.features.manager.project.member;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ProjectMemberRepository extends AbstractRepository {

	@Query("select pm FROM ProjectMember pm where pm.project.id = :projectId")
	List<ProjectMember> findProjectMembersById(int projectId);

}
