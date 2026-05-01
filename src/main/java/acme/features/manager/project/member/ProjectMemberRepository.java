
package acme.features.manager.project.member;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.projects.ProjectMember;

@Repository
public interface ProjectMemberRepository extends AbstractRepository {

	@Query("select pm FROM ProjectMember pm where pm.project.id = :projectId")
	List<ProjectMember> findProjectMembersByProjectId(int projectId);

	@Query("select p FROM Project p where p.id = :projectId")
	Project findProjectById(int projectId);

}
