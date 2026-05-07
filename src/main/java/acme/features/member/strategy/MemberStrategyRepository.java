
package acme.features.member.strategy;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.strategies.Strategy;

@Repository
public interface MemberStrategyRepository extends AbstractRepository {

	@Query("select s from Strategy s where s.project.id = :projectId")
	Collection<Strategy> findStrategiesByProjectId(int projectId);

	@Query("select count(mp) from ProjectMember mp where mp.project.id = :projectId and mp.member.id = :memberId")
	Integer checkProjectBelongsToMember(int projectId, int memberId);

	@Query("select s from Strategy s where s.id = :id")
	Strategy findStrategyById(int id);

}
