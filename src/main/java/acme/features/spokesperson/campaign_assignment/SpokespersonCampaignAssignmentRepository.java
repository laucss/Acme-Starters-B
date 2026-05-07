
package acme.features.spokesperson.campaign_assignment;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.campaigns.Campaign;
import acme.entities.projects.Project;

@Repository
public interface SpokespersonCampaignAssignmentRepository extends AbstractRepository {

	@Query("select p from Project p where p.id = :id")
	Project findProjectById(int id);

	@Query("select c from Campaign c where c.id = :id")
	Campaign findCampaignById(int id);

	@Query("select c from Campaign c where c.spokesperson.id = :spokespersonId and c.project is null")
	Collection<Campaign> findAvailableCampaignBySpokespersonId(int spokespersonId);

}
