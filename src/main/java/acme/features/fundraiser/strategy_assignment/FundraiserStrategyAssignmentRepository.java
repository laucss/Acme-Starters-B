
package acme.features.fundraiser.strategy_assignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.projects.Project;
import acme.entities.strategies.Strategy;

@Repository
public interface FundraiserStrategyAssignmentRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

	@Query("select s from Strategy s where s.fundraiser.id = :fundraiserId and s.project is null")
	Collection<Strategy> findAvailableStrategyByFundraiserId(int fundraiserId);

}
