
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.inventions.Invention;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;
import acme.entities.strategies.Tactic;

@Repository
public interface ManagerProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.manager.id = :managerId")
	Collection<Project> findProjectsByManagerId(int managerId);

	@Query("select p from Project p where p.id = :projectId")
	Project findProjectById(int projectId);

	@Query("select t from Tactic t where t.strategy.id = :id ")
	Collection<Tactic> findTacticsByStrategyId(int id);

	@Query("select count(t) from Tactic t where t.strategy.id = :strategyId")
	Long computeTacticsByStrategy(int strategyId);

	@Query("select count(i) from Invention i where i.project.id = :projectId")
	Long computeInventionByProjects(int projectId);

	@Query("select i from Invention i where i.project.id = :projectId")
	Collection<Invention> findInventionsByProjectId(int projectId);

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select c from Campaign c where c.project.id = :projectId")
	Collection<Campaign> findCampaignsByProjectId(int projectId);
}
