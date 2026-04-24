
package acme.entities.projects;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface ProjectRepository extends AbstractRepository {

	@Query("select p from Project p where p.title = :title")
	Project findProjectByTitle(String title);

	@Query("select sum(i.activesMonths) from Project p join p.inventions i where p.title = :title")
	Double sumInventions(String title);

	@Query("select sum(s.activesMonths) from Project p join p.strategies s where p.title = :title")
	Double sumStrategies(String title);

	@Query("select sum(c.activesMonths) from Project p join p.campaigns c where p.title = :title")
	Double sumCampaigns(String title);

}
