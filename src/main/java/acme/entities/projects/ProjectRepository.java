
package acme.entities.projects;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.strategies.Strategy;

@Repository
public interface ProjectRepository extends AbstractRepository {

	//@Query("select p from Project p where p.title = :title")
	//Project findProjectByTitle(String title);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select count(pm) from ProjectMember pm where pm.project.id = :projectId")
	Integer countByProjectId(int projectId);

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Long totalInventionsByProject(int projectId);

}
