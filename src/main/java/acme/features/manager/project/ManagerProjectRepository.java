
package acme.features.manager.project;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
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

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	Long computeInventionParts(int inventionId);

}
